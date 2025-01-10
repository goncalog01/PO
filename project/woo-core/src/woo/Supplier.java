package woo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class for suppliers. A supplier is characterized by its identifier, name,
 * address and state of activity.
 */
public class Supplier implements Serializable {

    /** The supplier's identifier. */
    private String key;

    /** The supplier's name. */
    private String name;

    /** The supplier's address. */
    private String address;

    /** The supplier's current state of activity. */
    private boolean active = true;

    private Map<Integer, Order> transactions = new TreeMap<Integer, Order>();

    /**
     * Constructor.
     * 
     * @param key     the supplier's identifier.
     * @param name    the supplier's name.
     * @param address the supplier's address.
     */
    public Supplier(String key, String name, String address) {
        this.key = key;
        this.name = name;
        this.address = address;
    }

    /**
     * @return the supplier's identifier.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the supplier's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the supplier's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the supplier's current state of activity.
     */
    public boolean isActive() {
        return active;
    }

    public String activityStr() {
        if (isActive())
            return "SIM";
        else
            return "NÃO";
    }

    public boolean toggleTransactions() {
        if (isActive())
            active = false;
        else
            active = true;

        return active;
    }

    public void addTransaction(Order order) {
        transactions.put(order.getKey(), order);
    }

    public Collection<Order> getTransactions() {
        return transactions.values();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Supplier) {
            Supplier supplier = (Supplier) o;
            return key.equals(supplier.getKey()) && name.equals(supplier.getName()) && address.equals(supplier.getAddress());
        }
        return false;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return getKey() + "|" + getName() + "|" + getAddress() + "|" + activityStr();
    }
}