package woo;

public enum SalePeriod {
    P1 {
        public void update(Sale sale, int date) {
            if (sale.getDeadline() - date < sale.getProduct().getPaymentRange()) {
                sale.setPeriod(SalePeriod.P2);
                sale.updatePeriod(date);
            }
        }
    },
    P2 {
        public void update(Sale sale, int date) {
            if (sale.getDeadline() - date < 0) {
                sale.setPeriod(SalePeriod.P3);
                sale.updatePeriod(date);
            }
        }
    },
    P3 {
        public void update(Sale sale, int date) {
            if (date - sale.getDeadline() > sale.getProduct().getPaymentRange())
                sale.setPeriod(SalePeriod.P4);
        }
    },
    P4 {
        public void update(Sale sale, int date) {}
    };

    public abstract void update(Sale sale, int date);
}
