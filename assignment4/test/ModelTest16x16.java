/**
 * Tests for the model on a 16x16 matrix image stored as an array in res/ing16x6/original.txt.
 * Expected outputs are also stored as arrays in text files.
 */
public class ModelTest16x16 extends AbstractModelTest {

  @Override
  protected String getRoot() {
    return "res/img16x16/";
  }
}
