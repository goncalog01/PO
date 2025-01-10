package woo.exceptions;

/** Exception for unknown service levels. */
public class NoSuchServiceLevelException extends Exception {
    
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192335L;

  /** Unknown level. */
  private String _level;

  /** @param level Unknown level to report. */
  public NoSuchServiceLevelException(String level) {
    _level = level;
  }

  /** @return the unknown level */
  public String getLevel() {
    return _level;
  }

}
