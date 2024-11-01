# Image Processing Application
This application processes images by applying various commands like load, brighten, blur, and others. It operates using a script-based command system, where users can run predefined scripts through a command-line interface.

## Instructions to Run the Application
### Prerequisites
Make sure the necessary resources (images, scripts) are in place:

> Image files are stored in the res/img/ directory.
> Scripts are located in the res/scripts/ directory.


### Running the Application

#### Start the Application
Run the main() function from app.App.java.

#### Enter Commands
You can enter the following commands to interact with the application:

Run a Script
To execute a script with predefined commands, enter:

run res/scripts/script.txt


Quit the Application
To exit the application, enter:

quit


Explanation of Commands in the Script
- load res/img/parrot.jpg parrot: Loads the image file parrot.jpg and refers to it as parrot for further operations.
- blur parrot blurred-parrot: Blurs the image parrot and saves the result as blurred-parrot.
- vertical-flip blurred-parrot blurred-parrot-flipped: Vertically flips blurred-parrot and saves it as blurred-parrot-flipped.
- save res/img/parrot-blurred-flipped.jpg blurred-parrot-flipped: Saves the flipped and blurred image.
- rgb-split parrot red green blue: Splits the parrot image into its red, green, and blue channels.
- brighten 50 red red: Brightens the red channel by 50.
- rgb-combine parrot-tinted red green blue: Recombines the red, green, and blue channels into a tinted version of the parrot image.
- sharpen parrot sharpen-parrot: Sharpens the parrot image.
- sepia sharpen-parrot sepia-parrot: Applies a sepia effect to the sharpened image.
- save res/img/parrot-sharpen-sepia.jpg sepia-parrot: Saves the sepia-toned image.


## Image License
The image used in res/img/parrot.jpg is:

"Blue Orange and Green Bird on Yellow Flower" by Mark Broadhurst, licensed under CC BY 4.0.
