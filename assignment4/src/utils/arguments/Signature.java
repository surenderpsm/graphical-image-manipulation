package utils.arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Signature {

  List<ArgumentType> signature;

  Signature(ArgumentType... signature) {
    this.signature = new ArrayList<ArgumentType>();
    this.signature.addAll(Arrays.asList(signature));
  }

  public ArgumentType getSignatureAt(int index) {
    if (index < 0 || index >= signature.size()) {
      throw new IndexOutOfBoundsException(
          "Index out of bounds in signature: " + index);
    }
    return signature.get(index);
  }
}
