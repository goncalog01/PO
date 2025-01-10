package woo.exceptions;

/** Exception for unknown product keys. */
public class NoSuchProductKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown key. */
  private String _key;

  /** @param key Unknown key to report. */
  public NoSuchProductKeyException(String key) {
    _key = key;
  }

  /**
   * @return the unknown key.
   */
  public String getKey() {
      return _key;
  }
}
