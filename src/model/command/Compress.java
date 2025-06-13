package model.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * Implements image compression using the Haar wavelet transform. This class compresses images by:
 * 1. Converting the image to the Haar wavelet domain
 * 2. Zeroing out the smallest coefficients based on a compression ratio
 * 3. Converting back to the spatial domain
 */
public class Compress extends AbstractCommand {

  private final int compressionRatio;
  private static final int BLACK_THRESHOLD = 10;
  private static final int MAX_COLOR_VALUE = 255;
  private static final int MIN_COLOR_VALUE = 0;

  /**
   * Constructs a new Compress processor.
   *
   * @param rawArguments The command arguments in format: "compressionRatio sourceImage
   *                     destinationImage"
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if compression ratio is invalid or arguments are incorrect
   */

  public Compress(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    compressionRatio = parseCompressionRatio(parseInt(0));
    currentImage = cache.get(parseString(1));
    imageName = parseString(2);
  }

  /**
   * Parses and validates the compression ratio from the input argument.
   *
   * @param value The compression ratio as a int
   * @return The parsed compression ratio as an integer
   * @throws IllegalArgumentException if the ratio is not between 0 and 100 or not a valid integer
   */

  private int parseCompressionRatio(int value) {
    try {
      if (value <= 0 || value >= 100) {
        throw new IllegalArgumentException(
            "Invalid compression range. " + " Expected between 0 and 100.");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected integer argument at position 0.");
    }
  }

  /**
   * Executes the compression operation on all color channels of the image.
   */
  @Override
  public void execute() {
    int[][][]
        imageArray =
        combineChannels(processColorChannel(currentImage.getRedChannelData()),
                        processColorChannel(currentImage.getGreenChannelData()),
                        processColorChannel(currentImage.getBlueChannelData()));
    cache.set(imageName, new Image(imageArray));
  }

  /**
   * Processes a single color channel through the wavelet transform and compression.
   *
   * @param channelData The 2D array representing one color channel
   * @return The processed channel data after compression and reconstruction
   */

  private int[][] processColorChannel(int[][] channelData) {
    double[][] transform = haar2D(arrayConverter.toDouble(channelData));
    zeroSmallestPercentage(transform, compressionRatio, BLACK_THRESHOLD);
    return arrayConverter.toInt(haar2DInverse(transform));
  }

  /**
   * Combines separate color channels into a single RGB image array.
   *
   * @param red   The processed red channel data
   * @param green The processed green channel data
   * @param blue  The processed blue channel data
   * @return A 3D array representing the combined RGB image
   */

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

  /**
   * method to clamp values between 0 and 255. to prevent them from going out of bound.
   *
   * @param value to be clamped.
   * @return clamped value.
   */
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

  /**
   * Sets the smallest n% of wavelet coefficients to zero.
   *
   * @param array          The wavelet coefficients
   * @param nPercent       The percentage of coefficients to zero out (0-100)
   * @param blackThreshold The minimum threshold for considering coefficients
   */

  public void zeroSmallestPercentage(double[][] array, int nPercent, int blackThreshold) {
    List<Double> significantValues = collectSignificantValues(array, blackThreshold);
    double threshold = calculateThreshold(significantValues, nPercent);
    applyThreshold(array, threshold);
  }

  /**
   * Collects significant wavelet coefficients above the black threshold.
   *
   * @param array          The wavelet coefficients array
   * @param blackThreshold The minimum threshold for considering coefficients
   * @return A list of significant coefficient values
   */

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

  /**
   * Calculates the threshold value for coefficient preservation based on the desired compression
   * ratio.
   *
   * @param values   List of coefficient values to analyze
   * @param nPercent The percentage of coefficients to preserve
   * @return The threshold value for coefficient preservation
   */

  private double calculateThreshold(List<Double> values, int nPercent) {
    Collections.sort(values);
    return values.get((int) (nPercent / 100.0 * values.size() - 1));
  }

  /**
   * Applies the threshold to the wavelet coefficients, zeroing out small values.
   *
   * @param array     The wavelet coefficients array
   * @param threshold The threshold value below which coefficients are zeroed
   */
  private static void applyThreshold(double[][] array, double threshold) {
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[0].length; j++) {
        if (Math.abs(array[i][j]) <= threshold) {
          array[i][j] = 0.0;
        }
      }
    }
  }

  /**
   * performs the haar 2d wavelet transform.
   *
   * @param arr 2d matrix
   * @return 2d haar transformed array.
   */
  private double[][] haar2D(double[][] arr) {
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

  /**
   * performs the haar 2d wavelet transform inverse.
   *
   * @param arr 2d matrix
   * @return 2d haar inverse array.
   */
  private double[][] haar2DInverse(double[][] arr) {
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

  /**
   * Performs a single-level Haar wavelet transform on a 1D array.
   *
   * @param s The input array to transform
   * @return The transformed array containing averages and differences
   */
  // Haar transform methods
  public double[] transform(double[] s) {
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

  /**
   * Performs a single-level inverse Haar wavelet transform on a 1D array.
   *
   * @param s The transformed array to inverse
   * @return The reconstructed array
   */

  public double[] inverse(double[] s) {
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

  /**
   * function to get a column of a matrix.
   *
   * @param matrix 2d array.
   * @param column column index.
   * @return array containing column values.
   */
  private double[] getColumn(double[][] matrix, int column) {
    double[] columnArr = new double[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      columnArr[i] = matrix[i][column];
    }
    return columnArr;
  }

  /**
   * function to calculate the required padding size.
   *
   * @param arr 2d array.
   * @return padding size.
   */
  private int calculatePaddedSize(double[][] arr) {
    int max = Math.max(arr.length, arr[0].length);
    int size = 1;
    while (size < max) {
      size *= 2;
    }
    return size;
  }

  /**
   * method to padd the matrix.
   *
   * @param arr  array to pad.
   * @param size size of the array.
   * @return padded matrix.
   */
  private double[][] padMatrix(double[][] arr, int size) {
    double[][] matrix = new double[size][size];
    for (int i = 0; i < arr.length; i++) {
      matrix[i] = Arrays.copyOf(arr[i], size);
    }
    return matrix;
  }

  /**
   * method to forwards transform rows.
   *
   * @param matrix matrix
   * @param c      till index
   */

  private void transformRows(double[][] matrix, int c) {
    for (int i = 0; i < c; i++) {
      double[] subArr = Arrays.copyOfRange(matrix[i], 0, c);
      subArr = transform(subArr);
      System.arraycopy(subArr, 0, matrix[i], 0, c);
    }
  }

  /**
   * method to forward transform columns.
   *
   * @param matrix matrix
   * @param c      till index
   */
  private void transformColumns(double[][] matrix, int c) {
    for (int j = 0; j < c; j++) {
      double[] subArr = Arrays.copyOfRange(getColumn(matrix, j), 0, c);
      subArr = transform(subArr);
      for (int i = 0; i < c; i++) {
        matrix[i][j] = subArr[i];
      }
    }
  }

  /**
   * method to inverse transform columns.
   *
   * @param matrix matrix
   * @param c      till index
   */
  private void inverseColumns(double[][] matrix, int c) {
    for (int j = 0; j < c; j++) {
      double[] subArr = Arrays.copyOfRange(getColumn(matrix, j), 0, c);
      subArr = inverse(subArr);
      for (int i = 0; i < c; i++) {
        matrix[i][j] = subArr[i];
      }
    }
  }

  /**
   * method to inverse transform rows.
   *
   * @param matrix matrix
   * @param c      till index
   */
  private void inverseRows(double[][] matrix, int c) {
    for (int i = 0; i < c; i++) {
      double[] subArr = Arrays.copyOfRange(matrix[i], 0, c);
      subArr = inverse(subArr);
      for (int j = 0; j < c; j++) {
        matrix[i][j] = subArr[j];
      }
    }
  }
}