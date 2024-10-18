package model.arg;

/**
 * The {@code ArgumentTypes} enum represents the possible types of arguments that can be passed to a
 * command in the image processing application.
 *
 * <h2>Available Argument Types:</h2>
 * <ul>
 *   <li>{@link #EXISTING_IMAGE} - Represents an existing image that can be processed or manipulated.</li>
 *   <li>{@link #NEW_IMAGE} - Represents a new image to be created or initialized in the application.</li>
 *   <li>{@link #FILE_OUT} - Represents a file output location where the result of the command will be saved.</li>
 *   <li>{@link #FILE_IN} - Represents a file input source from which an image will be loaded for processing.</li>
 *   <li>{@link #INTEGER} - Represents an integer value, which may be used for various parameters such as dimensions,
 *       counts, or other numeric configurations.</li>
 * </ul>
 */

public enum ArgumentType {
  EXISTING_IMAGE,
  NEW_IMAGE,
  FILE_OUT,
  FILE_IN,
  INTEGER;
}
