package woo.exceptions;

/** Exception thrown when a client key is duplicated. */
public class NonUniqueClientKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Client key. */
  private String _key;

  /** @param key the duplicated key */
  public NonUniqueClientKeyException(String key) {
    _key = key;
  }

  /** @return the duplicated key */
  public String getKey() {
    return _key;
  }
  
}
