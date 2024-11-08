
# Key Design Changes

## Model Interface Implementation
The Model class has been restructured to implement multiple interfaces, following the Interface Segregation Principle:

**Current Design:**
- Model implements three distinct interfaces:
    1. `ModelRunner`: Handles command execution
    2. `ImageCacheProvider`: Manages image operations
    3. `HistogramCacheProvider`: Manages histogram operations

**Benefits:**
- Separation of concerns
- Enables future extensions without modifying existing code


## Key Abstractions in `command` package

The library's architecture evolved to better handle different types of image operations:

1. **Initial Design**
- Commands extended either `AbstractCommand` or `Abstract2ArgCommand`
- All operations were treated uniformly regardless of their nature
- Worked well for operations like flip that operate on the whole image

2. **Pixel-based Operations**
- Operations like luma, brightness, sepia needed to work on individual pixels
- Introduced `ImageProcessor` to handle pixel-by-pixel operations
- Added `processImage()` method to standardize pixel processing

3. **Simple Transformations**
- For operations that transform each pixel independently (brightness, sepia, etc.)
- Introduced `SimpleImageProcessor` with `PixelTransformer` interface
- Uses lambda functions to define pixel-level transformations

4. **Kernel-based Filters**
- Operations like blur and sharpen depend on neighboring pixels
- Introduced `Filter` class extending `ImageProcessor`
- Handles convolution operations that need pixel neighborhoods

> The class hierarchy looks like this after refactoring:
>```
>AbstractCommand
>├── Abstract2ArgCommand (for whole-image operations)
>└── ImageProcessor (for pixel-based operations)
>    ├── SimpleImageProcessor (single-pixel transformations)
>    └── Filter (neighborhood operations)
>```

## Cache Implementation Changes
The cache system has undergone significant architectural improvements:

**Previous Version:**
- Cache was exclusively for images
- Implemented as a static inner class within the Image class
- Limited to single-type storage

**Current Version:**
- Standalone Cache class managed by the Model
- Supports multiple data types (images and histograms)
- Uses interface-based design for type safety
- Easily extensible for new data types

### How to extend:
To add support for a new data type:
1. Define a new interface (e.g., `NewTypeCacheable`)
2. Add the interface to Cache's implementation
3. Implement the required methods in Cache

```java
// Example of adding a new cacheable type
public interface NewTypeCacheable {
    NewType get(String name);
    void set(String name, NewType value);
}

public class Cache implements HistogramCacheable, ImageCacheable, NewTypeCacheable {
    // Implement new methods...
}
```
# Overview - Model Package

## `command` package
the command package maintains all commands. The CommmandEnum serves as the sole public entry point for the command package, representing a collection of commands for various operations in the image processing application. This class implements the factory pattern to facilitate the dynamic instantiation of command classes that implement the Command interface. 
### Add a new command:
- Create the class inside `command` package and extend `AbstractCommand`. 
- Add an entry into the `CommandEnum` enumeration.

```java
NEW_COMMAND(NewCommandClass.class, "command_name");
```

### Adding a New Command (V2)
- Determine if the new operation modifies each pixel individually; if so, it should extend `SimpleImageProcessor`.
- For kernel-based operations (e.g., blur or sharpen), extend `Filter`.
- If the operation processes the entire image at once (like a flip), extend `AbstractCommand`
 or `Abstract2ArgCommand` if it takes only 2 arguments namely: image and new image alias.
### Model
The `Model` class serves as the main entry point to the model package. It implements:
- Command execution system
- Image cache management
- Image retrieval and storage operations

#### Usage Example
```java
// Initialize the model
Model model = new Model();

// Execute a command
model.execute("blur", "koala koala_blurred");

// Retrieve an image
int[][][] image = model.getImage("koala");

// Store an image
model.setImage(image, "rabbit");
```

### Cache
The `Cache` class provides temporary storage for multiple data types using a HashMap-based implementation. It supports:
- Type-safe storage and retrieval through interfaces
- Support for images and histograms
- Extensible design for future data types

### Interfaces

#### ModelRunner
Defines the contract for executing commands within the model:
- `execute(String command, String args)`: Processes commands with their arguments

#### ImageCacheable
Handles image-related operations:
- `getImage(String name)`: Retrieves image data as a 3D array `[width][height][num_channels]`
- `setImage(int[][][] image, String name)`: Stores image data in the cache

#### HistogramCacheable
Manages histogram-related operations:
- `isHistogram(String name)`: Checks for histogram existence
- `getHistogram(String name)`: Retrieves histogram data as a 2D array `[num_channels][bins]`

## Data Formats

### Image Data
Images are stored as 3D arrays with the following dimensions:
- Width: Image width in pixels
- Height: Image height in pixels
- Channels: Number of color channels (e.g., RGB, RGBA)

### Histogram Data
Histograms are stored as 2D arrays with the following dimensions:
- Number of channels (default: 3)
- Number of bins (default: 256)
### Controller Package

The controller package manages user input and coordinates between the model and view:

#### Core Components

1. **Controller**: Main controller class that:
    - Manages input/output streams
    - Processes user commands
    - Handles script execution
    - Coordinates with the model

2. **CommandExecutor**:
    - Parses and executes individual commands
    - Routes commands to appropriate handlers

3. **IOHandler**:
    - Manages file input/output operations
    - Selects appropriate image handlers
    - Handles loading and saving of images

4. **ScriptHandler**:
    - Reads and processes script files
    - Filters comments and empty lines
    - Returns executable commands

5. **HistogramGenerator**:
    - Generates visual representations of image color distributions
    - Creates color-coded histograms for RGB channels
    - Outputs histogram as an image

# Image Licenses

Photo by mark broadhurst: https://www.pexels.com/photo/blue-orange-and-green-bird-on-yellow-flower-105808/

Photo by Srikanth Popuri: https://www.pexels.com/photo/close-up-photography-of-egyptian-starcluster-713777/

Photo by Lucie Liz: https://www.pexels.com/photo/doughnuts-on-pink-surface-4945142/