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
      if (mandate == null || mandate.getSignatureAt(id).isValidArgument(arg)) {
        super.setArgument(id, arg);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Argument does not match mandate at index " + id);
    }
  }
}
