package utils.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Signature {

  private final List<ArgumentType> signature;

  public static Signature define(ArgumentType... signature) {
    return new Signature(signature);
  }

  public static Signature EMPTY = new Signature();

  private Signature() {
    signature = new ArrayList<>();
  }

  private Signature(ArgumentType... signature) {
    this.signature = new ArrayList<ArgumentType>();
    this.signature.addAll(Arrays.asList(signature));
  }

  public ArgumentType getSignatureAt(int index) {
    if (index < 0 || index >= signature.size()) {
      throw new IndexOutOfBoundsException("Index out of bounds in signature: " + index);
    }
    return signature.get(index);
  }

  public boolean validateArgument(int index, Argument argument) {
    return getSignatureAt(index).isValidArgument(argument);
  }

  public int getLength(){
    return signature.size();
  }
}
