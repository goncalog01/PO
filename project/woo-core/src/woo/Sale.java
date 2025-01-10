package woo;

public class Sale extends Transaction {
    
    private Client client;
    private int deadline;
    private Product product;
    private int amount;
    private double valueToPay;
    private SalePeriod period = SalePeriod.P1;

    public Sale(int key, Client client, int deadline, Product product, int amount, int date) {
        super(key, product.getPrice() * amount);
        this.client = client;
        this.deadline = deadline;
        this.product = product;
        this.amount = amount;
        update(date);
        getClient().addTransaction(this);
    }

    public Client getClient() {
        return client;
    }

    public int getDeadline() {
        return deadline;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public double getValueToPay() {
        return valueToPay;
    }

    public int getValueToPayRounded() {
        return (int)Math.round(getValueToPay());
    }

    public void updateValueToPay(int date) {
        valueToPay = getClient().salePrice(this, date);
    }

    public SalePeriod getPeriod() {
        return period;
    }

    public void setPeriod(SalePeriod period) {
        this.period = period;
    }

    public void updatePeriod(int date) {
        period.update(this, date);
    }

    @Override
    public void update(int date) {
        updatePeriod(date);
        updateValueToPay(date);
    }

    @Override
    public void pay(int date) {
        setPaymentDate(date);
        getClient().addPayment(this);
    }

    @Override
    public double getBalanceValue() {
        return getValueToPay();
    }

    @Override
    public String toString() {
        return super.toString() + getClient().getKey() + "|" + getProduct().getKey() + "|" + getAmount() + "|" + getBaseValue() + "|" + getValueToPayRounded() + "|" + getDeadline() + paymentDateStr();
    }
}
