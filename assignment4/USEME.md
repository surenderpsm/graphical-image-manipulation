
# Starting the Application
## Script Mode
You can directly execute the sample script file by passing it as a command-line argument:
```bash
java -jar res/image-processor.jar -file res/scripts/script.txt
```
This allows for automated batch processing without entering interactive mode.

## Interactive Mode
```bash
java -jar res/image-processor.jar
```
Upon running it, you can enter commands to carry out image processing.

## Command Overview

| Command                 | Description                                                                                   | Parameters                                                             | Example Usage                                                                                               |
|-------------------------|-----------------------------------------------------------------------------------------------|------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| `load`                  | Loads an image file into memory.                                                              | `<file_path> <alias>`                                                  | `load flowers.jpg flowers`                                                                                  |
| `brighten`              | Adjusts the brightness of an image. A positive value brightens, and a negative value darkens. | `<amount> <input_alias> <output_alias> [split <value>]`                | `brighten 50 flowers res` or `brighten -45 parrot res split 50`                                            |
| `vertical-flip`         | Flips the image vertically.                                                                   | `<input_alias> <output_alias>`                                         | `vertical-flip flowers res`                                                                                 |
| `horizontal-flip`       | Flips the image horizontally.                                                                 | `<input_alias> <output_alias>`                                         | `horizontal-flip parrot res`                                                                                |
| `rgb-split`             | Splits the image into red, green, and blue component images.                                  | `<input_alias> <red_alias> <green_alias> <blue_alias>`                 | `rgb-split parrot red green blue`                                                                           |
| `rgb-combine`           | Combines red, green, and blue component images into a single color image.                    | `<output_alias> <red_alias> <green_alias> <blue_alias>`                | `rgb-combine res red green blue`                                                                            |
| `blur`                  | Applies a blur effect to the image.                                                           | `<input_alias> <output_alias> [split <value>]`                         | `blur donuts res` or `blur donuts res split 50`                                                             |
| `sharpen`               | Sharpens the image to enhance details.                                                        | `<input_alias> <output_alias> [split <value>]`                         | `sharpen flowers res` or `sharpen flowers res split 50`                                                    |
| `sepia`                 | Applies a sepia tone to the image.                                                            | `<input_alias> <output_alias> [split <value>]`                         | `sepia donuts res` or `sepia donuts res split 50`                                                           |
| `red-component`         | Extracts the red color component from the image.                                              | `<input_alias> <output_alias>`                                         | `red-component parrot res`                                                                                  |
| `green-component`       | Extracts the green color component from the image.                                            | `<input_alias> <output_alias>`                                         | `green-component parrot res`                                                                                |
| `blue-component`        | Extracts the blue color component from the image.                                             | `<input_alias> <output_alias>`                                         | `blue-component parrot res`                                                                                 |
| `value-component`       | Computes the brightness value component of the image.                                         | `<input_alias> <output_alias>`                                         | `value-component donuts res`                                                                                |
| `intensity-component`   | Extracts the average intensity component based on RGB values.                                 | `<input_alias> <output_alias>`                                         | `intensity-component donuts res`                                                                            |
| `luma-component`        | Extracts the luma (grayscale brightness) component of the image.                              | `<input_alias> <output_alias>`                                         | `luma-component donuts res`                                                                                 |
| `histogram`             | Generates a histogram of pixel values for analysis.                                           | `<input_alias> <output_alias>`                                         | `histogram donuts res`                                                                                      |
| `compress`              | Compresses the image to reduce file size.                                                     | `<quality> <input_alias> <output_alias>`                               | `compress 95 donuts res`                                                                                    |
| `grayscale`             | Converts the image to grayscale.                                                              | `<input_alias> <output_alias> [split <value>]`                         | `grayscale donuts res split 50`                                                                             |
| `color-correct`         | Adjusts colors in the image for improved color accuracy or effect.                            | `<input_alias> <output_alias> [split <value>]`                         | `color-correct donuts res split 75`                                                                         |
| `levels-adjust`         | Adjusts brightness and contrast levels based on black, midpoint, and white levels.            | `<black> <mid> <white> <input_alias> <output_alias> [split <value>]`   | `levels-adjust 0 90 245 parrot res split 50`                                                                |
| `save`                  | Saves an image from memory to a file.                                                         | `<file_path> <alias>`                                                  | `save res/img/output/flowers_brighten50.jpg res`                                                            |
