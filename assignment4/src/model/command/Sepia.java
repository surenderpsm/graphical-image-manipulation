package model.command;


class Sepia extends ColorTransform {

  public Sepia(String rawArguments) {
    super(rawArguments);
    a11 = 0.393;
    a12 = 0.769;
    a13 = 0.189;
    a21 = 0.349;
    a22 = 0.686;
    a23 = 0.168;
    a31 = 0.272;
    a32 = 0.534;
    a33 = 0.131;
  }


}
