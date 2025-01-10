package woo;

import woo.exceptions.NotEnoughProductException;

/**
 * Class for products. A product is characterized by its identifier, supplier,
 * price and stock.
 */
public class Product extends Subject {

    /** The product's identifier. */
    private String key;

    /** The product's supplier. */
    private Supplier supplier;

    /** The product's current price. */
    private int price;

    /** The product's stock critical level. */
    private int criticalLevel;

    /** The product's current stock. */
    private int stock = 0;

    private int paymentRange;

    /**
     * Constructor.
     * 
     * @param key           the product's identifier.
     * @param supplier      the product's supplier.
     * @param price         the product's initial price.
     * @param criticalLevel the product's stock critical level.
     */
    public Product(String key, Supplier supplier, int price, int criticalLevel, int paymentRange) {
        this.key = key;
        this.supplier = supplier;
        this.price = price;
        this.criticalLevel = criticalLevel;
        this.paymentRange = paymentRange;
    }

    /**
     * Constructor.
     * 
     * @param key           the product's identifier.
     * @param supplier      the product's supplier.
     * @param price         the product's initial price.
     * @param criticalLevel the product's stock critical level.
     * @param stock         the product's initial stock.
     */
    public Product(String key, Supplier supplier, int price, int criticalLevel, int stock, int paymentRange) {
        this.key = key;
        this.supplier = supplier;
        this.price = price;
        this.criticalLevel = criticalLevel;
        this.stock = stock;
        this.paymentRange = paymentRange;
    }

    /**
     * @return the product's identifier.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the product's supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @return the product's current price.
     */
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price >= 0) {
            if (this.price > price)
                notifyObservers(new Notification(NotificationType.BARGAIN, this, price));
            this.price = price;
        }
    }

    /**
     * @return the product's stock critical level.
     */
    public int getCriticalLevel() {
        return criticalLevel;
    }

    /**
     * @return the product's current stock.
     */
    public int getStock() {
        return stock;
    }

    public void addStock(int quantity) {
        if (stock == 0)
            notifyObservers(new Notification(NotificationType.NEW, this, getPrice()));
            
        stock += quantity;
    }

    public void removeStock(int quantity) throws NotEnoughProductException {
        if (stock - quantity >= 0)
            stock -= quantity;
        else
            throw new NotEnoughProductException(key, quantity, stock);
    }

    public int getPaymentRange() {
        return paymentRange;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "|" + getKey() + "|" + getSupplier().getKey() + "|" + getPrice() + "|" + getCriticalLevel() + "|" + getStock() + "|";
    }
}