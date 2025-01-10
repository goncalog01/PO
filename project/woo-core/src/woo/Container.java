package woo;

import woo.exceptions.NoSuchServiceTypeException;
import woo.exceptions.NoSuchServiceLevelException;

/**
 * Class for containers. A container is a product characterized by its service
 * type and level.
 */
public class Container extends Box {

    /** The container's service level. */
    private ServiceLevel level;

    /**
     * Constructor.
     * 
     * @param key           the container's identifier.
     * @param supplier      the container's supplier.
     * @param price         the container's initial price.
     * @param criticalLevel the container's stock critical level.
     * @param type          the container's service type.
     * @param level         the container's service level.
     * @throws NoSuchServiceTypeException
     * @throws NoSuchServiceLevelException
     */
    public Container(String key, Supplier supplier, int price, int criticalLevel, String type, String level) throws NoSuchServiceTypeException, NoSuchServiceLevelException {
        super(key, supplier, price, criticalLevel, type, 8);
        switch (level) {
            case "B4":
              this.level = ServiceLevel.B4;
              break;
            case "C4":
              this.level = ServiceLevel.C4;
              break;
            case "C5":
              this.level = ServiceLevel.C5;
              break;
            case "DL":
              this.level = ServiceLevel.DL;
              break;
            default:
              throw new NoSuchServiceLevelException(level);
          }
    }

    /**
     * Constructor.
     * 
     * @param key           the container's identifier.
     * @param supplier      the container's supplier.
     * @param price         the container's initial price.
     * @param criticalLevel the container's stock critical level.
     * @param stock         the container's initial stock.
     * @param type          the container's service type.
     * @param level         the container's service level.
     * @throws NoSuchServiceTypeException
     * @throws NoSuchServiceLevelException
     */
    public Container(String key, Supplier supplier, int price, int criticalLevel, int stock, String type, String level) throws NoSuchServiceTypeException, NoSuchServiceLevelException {
        super(key, supplier, price, criticalLevel, stock, type, 8);
        switch (level) {
            case "B4":
              this.level = ServiceLevel.B4;
              break;
            case "C4":
              this.level = ServiceLevel.C4;
              break;
            case "C5":
              this.level = ServiceLevel.C5;
              break;
            case "DL":
              this.level = ServiceLevel.DL;
              break;
            default:
              throw new NoSuchServiceLevelException(level);
        }    
    }

    /**
     * @return the container's service level.
     */
    public String getLevel() {
        return level.name();
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "CONTAINER" + getAttrs() + "|" + getLevel();
    }
}