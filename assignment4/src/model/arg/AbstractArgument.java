package model.arg;

import model.Image;

abstract class AbstractArgument implements Argument {

  private final ArgumentType type;
  private Object value;

  public AbstractArgument(ArgumentType type, Object value) {
    this.type = type;
    this.value = value;
  }

  public AbstractArgument(ArgumentType type) {
    this.type = type;
  }

  public void setValue(Object object) {
    switch (type) {
      case EXISTING_IMAGE:
        try {
          String name = (String) object;
          Image.Cache.get((String) object);
        } catch (Exception e) {
        }

    }
  }

  public Object getValue() {
    return value;
  }
}
