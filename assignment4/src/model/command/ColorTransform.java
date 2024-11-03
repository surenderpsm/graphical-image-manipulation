package model.command;


import model.Cache;

class ColorTransform extends ImageProcessor {

  protected final double[][] matrix;

  public ColorTransform(String rawArguments, double[][] matrix, Cache cache) {
    super(rawArguments, cache);
    this.matrix = matrix;
  }

  @Override
  public void execute() {
    processImage((r, g, b) -> {
      int[] result = new int[3];
      for (int i = 0; i < 3; i++) {
        result[i] = clamp(
            (int) Math.round(matrix[i][0] * r + matrix[i][1] * g + matrix[i][2] * b));
      }
      return result;
    });
  }
}