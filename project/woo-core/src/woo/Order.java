package woo;

import java.util.Map;

public class Order extends Transaction {

    private Supplier supplier;
    private Map<Product, Integer> products;

    public Order(int key, Supplier supplier, Map<Product, Integer> products, int baseValue) {
        super(key, baseValue);
        this.supplier = supplier;
        this.products = products;
        getSupplier().addTransaction(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String getProducts() {
        String s = "";
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int amount = entry.getValue();
            s = s + product.getKey() + "|" + amount + "\n";
        }
        return s;
    }

    @Override
    public void update(int date) {}

    @Override
    public void pay(int date) {
        setPaymentDate(date);
    }

    @Override
    public double getBalanceValue() {
        return getBaseValue() * -1;
    }

    @Override
    public String toString() {
        return super.toString() + getSupplier().getKey() + "|" + getBaseValue() + paymentDateStr() + "\n" + getProducts();
    }
}
