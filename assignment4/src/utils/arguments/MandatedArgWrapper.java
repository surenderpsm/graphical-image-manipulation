package utils.arguments;

/**
 * This {@link ArgumentWrapper} supports a mandate which can be used to define a signature. The
 * added arguments are then validated with the defined signature.
 */
public class MandatedArgWrapper extends ArgumentWrapper {

  private final Signature mandate;

  /**
   * Creates an empty ArgumentWrapper, to which arguments can be added later.
   */
  public MandatedArgWrapper(Signature signature) {
    super();
    this.mandate = signature;
  }

  @Override
  public void setArgument(int id, Argument arg) {
    try {
      if (mandate == null || mandate.validateArgument(id,arg)) {
        super.setArgument(id, arg);
      } else{
        throw new IllegalArgumentException("Argument does not match mandate at index " + id);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Signature length does not match arguments.");
    }
  }

  public void setArgument(int index, Object arg) {
    setArgument(index, expectedAt(index).prepareArgument(arg));
  }

  public ArgumentType expectedAt(int index){
    try {
      return mandate.getSignatureAt(index);
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("No argument at index " + index);
    }
  }

  public int expectedLength(){
    return mandate.getLength();
  }

}
