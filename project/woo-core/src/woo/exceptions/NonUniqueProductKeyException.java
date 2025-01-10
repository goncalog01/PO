package woo.exceptions;

/** Exception thrown when a product key is duplicated. */
public class NonUniqueProductKeyException extends Exception {
  
  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Product key. */
  private String _key;

  /** @param key the duplicated key */
  public NonUniqueProductKeyException(String key) {
    _key = key;
  }

  /** @return the duplicated key */
  public String getKey() {
    return _key;
  }
  
}
