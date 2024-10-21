package model.command;


class Greyscale extends ColorTransform {

  public Sepia(String rawArguments) {
    a11 = 0.2126;
    a12 = 0.7152;
    a13 = 0.0722;
    a21 = 0.2126;
    a22 = 0.7152;
    a23 = 0.0722;
    a31 = 0.2126;
    a32 = 0.7152;
    a33 = 0.0722;
    super(rawArguments);
  }


}