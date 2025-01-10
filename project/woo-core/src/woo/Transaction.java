package woo;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

    private int key;
    private int baseValue;
    private int paymentDate;
    private boolean paid = false;

    public Transaction(int key, int baseValue) {
        this.key = key;
        this.baseValue = baseValue;
    }

    public int getKey() {
        return key;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public int getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(int date) {
        if (!wasPaid()) {
            paymentDate = date;
            paid = true;
        }
    }

    public String paymentDateStr() {
        if (wasPaid())
            return "|" + getPaymentDate();
        else
            return "";
    }

    public boolean wasPaid() {
        return paid;
    }

    public abstract void update(int date);

    public abstract void pay(int date);

    public abstract double getBalanceValue();

    @Override
    public String toString() {
        return getKey() + "|";
    }
}
