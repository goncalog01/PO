package woo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class for clients. A client is characterized by its identifier, name, address
 * and status.
 */
public class Client implements Observer, Serializable {

    /** The client's identifier. */
    private String key;

    /** The client's name. */
    private String name;

    /** The client's address. */
    private String address;

    /** The client's current status. */
    private ClientStatus status = ClientStatus.NORMAL;

    /** The client's current number of points. */
    private double points = 0;

    /** The total value of the purchases made by the client. */
    private int purchasesMadeValue = 0;

    /** The total value of the purchases paid by the client. */
    private double purchasesPaidValue = 0;

    private DeliveryMethod deliveryMethod = DeliveryMethod.APP;

    private Map<Integer, Sale> transactions = new TreeMap<Integer, Sale>();

    private Map<Integer, Sale> payments = new TreeMap<Integer, Sale>();

    private List<Notification> notifications = new ArrayList<Notification>();

    /**
     * Constructor.
     * 
     * @param key     the client's identifier.
     * @param name    the client's name.
     * @param address the client's address.
     */
    public Client(String key, String name, String address) {
        this.key = key;
        this.name = name;
        this.address = address;
    }

    /**
     * @return the client's identifier.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the client's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the client's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the client's current status.
     */
    public String getStatus() {
        return status.name();
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public void updateStatus() {
        status.update(this);
    }

    public double salePrice(Sale sale, int date) {
        return status.salePrice(sale, date);
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void addPoints(double points) {
        this.points += points;
        updateStatus();
    }

    public void removePoints(Sale sale) {
        status.removePoints(this, sale);
    }

    /**
     * @return the total value of the purchases made by the client.
     */
    public int getPurchasesMadeValue() {
        return purchasesMadeValue;
    }

    /**
     * @return the total value of the purchases paid by the client.
     */
    public int getPurchasesPaidValue() {
        return (int)Math.round(purchasesPaidValue);
    }

    public void setDeliveryMethod(DeliveryMethod method) {
        deliveryMethod = method;
    }

    @Override
    public void update(Notification notification) {
        deliveryMethod.processNotification(this, notification);
    }

    public void addTransaction(Sale sale) {
        transactions.put(sale.getKey(), sale);
        purchasesMadeValue += sale.getBaseValue();
    }

    public Collection<Sale> getTransactions() {
        return transactions.values();
    }

    public void addPayment(Sale sale) {
        payments.put(sale.getKey(), sale);
        purchasesPaidValue += sale.getValueToPay();
        
        if (sale.getPaymentDate() <= sale.getDeadline())
            addPoints(sale.getValueToPay() * 10);
        else
            removePoints(sale);
    }

    public Collection<Sale> getPayments() {
        return payments.values();
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public String getNotifications() {
        String s = "";
        for (Notification n : notifications)
            s = s + n.toString() + "\n";
        clearNotifications();
        return s;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return getKey() + "|" + getName() + "|" + getAddress() + "|" + getStatus() + "|" + getPurchasesMadeValue() + "|"
                + getPurchasesPaidValue();
    }
}