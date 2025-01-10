package woo;

import java.io.Serializable;

public class Notification implements Serializable {
    
    private NotificationType type;
    private Product product;
    private int price;

    public Notification(NotificationType type, Product product, int price) {
        this.type = type;
        this.product = product;
        this.price = price;
    }

    public String getType() {
        return type.name();
    }

    public Product getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return getType() + "|" + getProduct().getKey() + "|" + getPrice();
    }
}
