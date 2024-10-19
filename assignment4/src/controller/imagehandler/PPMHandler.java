package controller.imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.Image;

class PPMHandler extends AbstractImageHandler {

  PPMHandler(String path) throws FileNotFoundException {
    super(path);
  }

  @Override
  public void loadImage() {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(getPath()));
    } catch (FileNotFoundException e) {
      System.out.println("File " + getPath() + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    int[][][] image = new int[width][height][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // if values greater than max value, then clamp at maxValue.
        int r = sc.nextInt();
        if (r > maxValue) {
          maxValue = r;
        }
        image[i][j][0] = r;
        int g = sc.nextInt();
        if (g > maxValue) {
          maxValue = g;
        }
        image[i][j][1] = g;
        int b = sc.nextInt();
        if (b > maxValue) {
          maxValue = b;
        }
        image[i][j][2] = b;
      }
    }

    setImage(new Image(image));
  }
}

