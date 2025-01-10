package woo.exceptions;

/** Exception for unknown service types. */
public class NoSuchServiceTypeException extends Exception {
    
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown type. */
  private String _type;

  /** @param type Unknown type to report. */
  public NoSuchServiceTypeException(String type) {
    _type = type;
  }

  /** @return the unknown type */
  public String getType() {
    return _type;
  }

}
