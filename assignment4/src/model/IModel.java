package model;

import java.util.Map;
import utils.arguments.Signature;

public interface IModel extends ModelRunner, ImageCacheProvider, HistogramCacheProvider {

  /**
   * The controller or any other calling object can access a map of command names to argument
   * Signature.
   * @return String keys mapping to Signatures.
   */
  Map<String, Signature> getCommandSignatures();
}
