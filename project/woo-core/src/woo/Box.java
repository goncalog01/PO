package woo;

import woo.exceptions.NoSuchServiceTypeException;

/**
 * Class for boxes. A box is a product characterized by its service type.
 */
public class Box extends Product {

    /** The box's service type. */
    private ServiceType type;

    /**
     * Constructor.
     * 
     * @param key           the box's identifier.
     * @param supplier      the box's supplier.
     * @param price         the box's initial price.
     * @param criticalLevel the box's stock critical level.
     * @param type          the box's service type.
     * @throws NoSuchServiceTypeException
     */
    public Box(String key, Supplier supplier, int price, int criticalLevel, String type) throws NoSuchServiceTypeException {
        super(key, supplier, price, criticalLevel, 5);
        switch (type) {
            case "NORMAL":
              this.type = ServiceType.NORMAL;
              break;
            case "AIR":
              this.type = ServiceType.AIR;
              break;
            case "EXPRESS":
              this.type = ServiceType.EXPRESS;
              break;
            case "PERSONAL":
              this.type = ServiceType.PERSONAL;
              break;
            default:
              throw new NoSuchServiceTypeException(type);
          }
    }

    public Box(String key, Supplier supplier, int price, int criticalLevel, String type, int paymentRange) throws NoSuchServiceTypeException {
      super(key, supplier, price, criticalLevel, paymentRange);
      switch (type) {
          case "NORMAL":
            this.type = ServiceType.NORMAL;
            break;
          case "AIR":
            this.type = ServiceType.AIR;
            break;
          case "EXPRESS":
            this.type = ServiceType.EXPRESS;
            break;
          case "PERSONAL":
            this.type = ServiceType.PERSONAL;
            break;
          default:
            throw new NoSuchServiceTypeException(type);
        }
  }

    /**
     * Constructor.
     * 
     * @param key           the box's identifier.
     * @param supplier      the box's supplier.
     * @param price         the box's initial price.
     * @param criticalLevel the box's stock critical level.
     * @param stock         the box's initial stock.
     * @param type          the box's service type.
     * @throws NoSuchServiceTypeException
     */
    public Box(String key, Supplier supplier, int price, int criticalLevel, int stock, String type) throws NoSuchServiceTypeException {
        super(key, supplier, price, criticalLevel, stock, 5);
        switch (type) {
            case "NORMAL":
              this.type = ServiceType.NORMAL;
              break;
            case "AIR":
              this.type = ServiceType.AIR;
              break;
            case "EXPRESS":
              this.type = ServiceType.EXPRESS;
              break;
            case "PERSONAL":
              this.type = ServiceType.PERSONAL;
              break;
            default:
              throw new NoSuchServiceTypeException(type);
          }
    }

    public Box(String key, Supplier supplier, int price, int criticalLevel, int stock, String type, int paymentRange) throws NoSuchServiceTypeException {
      super(key, supplier, price, criticalLevel, stock, paymentRange);
      switch (type) {
          case "NORMAL":
            this.type = ServiceType.NORMAL;
            break;
          case "AIR":
            this.type = ServiceType.AIR;
            break;
          case "EXPRESS":
            this.type = ServiceType.EXPRESS;
            break;
          case "PERSONAL":
            this.type = ServiceType.PERSONAL;
            break;
          default:
            throw new NoSuchServiceTypeException(type);
        }
  }

    /**
     * @return the box's service type.
     */
    public String getType() {
        return type.name();
    }

    /**
     * @return a string with all of the box's attributes.
     */
    public String getAttrs() {
        return super.toString() + getType();
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BOX" + getAttrs();
    }
}