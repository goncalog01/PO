package woo.exceptions;

/** Exception for reporting unauthorized supplier attempts. */
public class NonAuthorizedSupplierException extends Exception {
    
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009200054L;

  /** Unknown key. */
  private String _key;

  /** @param key unauthorized key to report. */
  public NonAuthorizedSupplierException(String key) {
    _key = key;
  }

  public String getKey() {
      return _key;
  }
}
