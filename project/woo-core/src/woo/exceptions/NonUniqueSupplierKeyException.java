package woo.exceptions;

/** Exception thrown when a supplier key is duplicated. */
public class NonUniqueSupplierKeyException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Supplier key. */
  private String _key;

  /** @param key the duplicated key */
  public NonUniqueSupplierKeyException(String key) {
    _key = key;
  }

  /** @return the duplicated key */
  public String getKey() {
    return _key;
  }

}
