package model.command;
// @todo Refactor
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Cache;
import model.Image;

public class Compress extends AbstractCommand {
  private final int compressionRatio;
  private static final int BLACK_THRESHOLD = 10;
  private static final int MAX_COLOR_VALUE = 255;
  private static final int MIN_COLOR_VALUE = 0;

  public Compress(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    compressionRatio = parseCompressionRatio(getArg(0));
    currentImage = cache.get(getArg(1));
    imageName = getArg(2);
  }

  private int parseCompressionRatio(String ratio) {
    try {
      int value = Integer.parseInt(ratio);
      if (value <= 0 || value >= 100) {
        throw new IllegalArgumentException(
            "Invalid compression range. Expected between 0 and 100.");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected integer argument at position 0.");
    }
  }

  @Override
  public void execute() {
    int[][] processedRed = processColorChannel(currentImage.getRedChannelData());
    int[][] processedGreen = processColorChannel(currentImage.getGreenChannelData());
    int[][] processedBlue = processColorChannel(currentImage.getBlueChannelData());

    int[][][] imageArray = combineChannels(processedRed, processedGreen, processedBlue);
    cache.set(imageName, new Image(imageArray));
  }

  private int[][] processColorChannel(int[][] channelData) {
    double[][] transform = haar2D(arrayConverter.toDouble(channelData));
    zeroSmallestPercentage(transform, compressionRatio, BLACK_THRESHOLD);
    return arrayConverter.toInt(haar2DInverse(transform));
  }

  private int[][][] combineChannels(int[][] red, int[][] green, int[][] blue) {
    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int[][][] result = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        result[i][j][0] = clamp(red[i][j]);
        result[i][j][1] = clamp(green[i][j]);
        result[i][j][2] = clamp(blue[i][j]);
      }
    }
    return result;
  }

  private static int clamp(int value) {
    return Math.min(Compress.MAX_COLOR_VALUE, Math.max(Compress.MIN_COLOR_VALUE, value));
  }

  // Array conversion utility class
  private static class ArrayConverter {
    public double[][] toDouble(int[][] array) {
      double[][] result = new double[array.length][array[0].length];
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[0].length; j++) {
          result[i][j] = array[i][j];
        }
      }
      return result;
    }

    public int[][] toInt(double[][] array) {
      int[][] result = new int[array.length][array[0].length];
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array[0].length; j++) {
          result[i][j] = (int) Math.round(array[i][j]);
        }
      }
      return result;
    }
  }
  private final ArrayConverter arrayConverter = new ArrayConverter();

  public static void zeroSmallestPercentage(double[][] array, int nPercent, int blackThreshold) {
    List<Double> significantValues = collectSignificantValues(array, blackThreshold);
    double threshold = calculateThreshold(significantValues, nPercent);
    applyThreshold(array, threshold);
  }

  private static List<Double> collectSignificantValues(double[][] array, int blackThreshold) {
    List<Double> values = new ArrayList<>();
    for (double[] row : array) {
      for (double value : row) {
        if (Math.abs(value) > blackThreshold) {
          values.add(Math.abs(value));
        }
      }
    }
    return values;
  }

  private static double calculateThreshold(List<Double> values, int nPercent) {
    Collections.sort(values);
    return values.get((int) (nPercent / 100.0 * values.size() - 1));
  }

  private static void applyThreshold(double[][] array, double threshold) {
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[0].length; j++) {
        if (Math.abs(array[i][j]) <= threshold) {
          array[i][j] = 0.0;
        }
      }
    }
  }

  // Haar transform methods
  public static double[] transform(double[] s) {
    int size = s.length / 2;
    double[] avg = new double[size];
    double[] diff = new double[size];

    for (int i = 0, j = 0; i < s.length; i = i + 2, j++) {
      double a = s[i];
      double b = s[i + 1];
      avg[j] = (a + b) / Math.sqrt(2);
      diff[j] = (a - b) / Math.sqrt(2);
    }

    double[] result = new double[s.length];
    System.arraycopy(avg, 0, result, 0, size);
    System.arraycopy(diff, 0, result, size, size);
    return result;
  }

  public static double[] inverse(double[] s) {
    int half = s.length / 2;
    double[] result = new double[s.length];
    List<Double> temp = new ArrayList<>();

    for (int i = 0, j = half; i < half; i++, j++) {
      double a = s[i];
      double b = s[j];
      temp.add((a + b) / Math.sqrt(2));
      temp.add((a - b) / Math.sqrt(2));
    }

    for (int i = 0; i < temp.size(); i++) {
      result[i] = temp.get(i);
    }
    return result;
  }

  private static double[] getColumn(double[][] matrix, int column) {
    double[] columnArr = new double[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      columnArr[i] = matrix[i][column];
    }
    return columnArr;
  }

  public static double[][] haar2D(double[][] arr) {
    int size = calculatePaddedSize(arr);
    double[][] matrix = padMatrix(arr, size);

    int c = size;
    while (c > 1) {
      transformRows(matrix, c);
      transformColumns(matrix, c);
      c = c / 2;
    }
    return matrix;
  }

  public static double[][] haar2DInverse(double[][] arr) {
    int size = calculatePaddedSize(arr);
    double[][] matrix = padMatrix(arr, size);

    int c = 2;
    while (c <= size) {
      inverseColumns(matrix, c);
      inverseRows(matrix, c);
      c = c * 2;
    }
    return matrix;
  }

  private static int calculatePaddedSize(double[][] arr) {
    int max = Math.max(arr.length, arr[0].length);
    int size = 1;
    while (size < max) {
      size *= 2;
    }
    return size;
  }

  private static double[][] padMatrix(double[][] arr, int size) {
    double[][] matrix = new double[size][size];
    for (int i = 0; i < arr.length; i++) {
      matrix[i] = Arrays.copyOf(arr[i], size);
    }
    return matrix;
  }

  private static void transformRows(double[][] matrix, int c) {
    for (int i = 0; i < c; i++) {
      double[] subArr = Arrays.copyOfRange(matrix[i], 0, c);
      subArr = transform(subArr);
      System.arraycopy(subArr, 0, matrix[i], 0, c);
    }
  }

  private static void transformColumns(double[][] matrix, int c) {
    for (int j = 0; j < c; j++) {
      double[] subArr = Arrays.copyOfRange(getColumn(matrix, j), 0, c);
      subArr = transform(subArr);
      for (int i = 0; i < c; i++) {
        matrix[i][j] = subArr[i];
      }
    }
  }

  private static void inverseColumns(double[][] matrix, int c) {
    for (int j = 0; j < c; j++) {
      double[] subArr = Arrays.copyOfRange(getColumn(matrix, j), 0, c);
      subArr = inverse(subArr);
      for (int i = 0; i < c; i++) {
        matrix[i][j] = subArr[i];
      }
    }
  }

  private static void inverseRows(double[][] matrix, int c) {
    for (int i = 0; i < c; i++) {
      double[] subArr = Arrays.copyOfRange(matrix[i], 0, c);
      subArr = inverse(subArr);
      for (int j = 0; j < c; j++) {
        matrix[i][j] = subArr[j];
      }
    }
  }
}