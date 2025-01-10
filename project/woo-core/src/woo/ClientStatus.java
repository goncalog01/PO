package woo;

public enum ClientStatus {
    NORMAL {
        public void update(Client client) {
            if (client.getPoints() > 2000) {
                client.setStatus(ClientStatus.SELECTION);
                client.updateStatus();
            }
        }

        public double salePrice(Sale sale, int date) {
            double price = 0;
            int daysLate = date - sale.getDeadline();
            switch (sale.getPeriod()) {
                case P1:
                    price = sale.getBaseValue() * 0.9;
                    break;
                case P2:
                    price = sale.getBaseValue();
                    break;
                case P3:
                    price = sale.getBaseValue() + 0.05 * sale.getBaseValue() * daysLate;
                    break;
                case P4:
                    price = sale.getBaseValue() + 0.1 * sale.getBaseValue() * daysLate;
            }
            return price;
        }

        public void removePoints(Client client, Sale sale) {}
    },
    SELECTION {
        public void update(Client client) {
            if (client.getPoints() <= 2000)
                client.setStatus(ClientStatus.NORMAL);
            else if (client.getPoints() > 25000)
                client.setStatus(ClientStatus.ELITE);
        }

        public double salePrice(Sale sale, int date) {
            double price = 0;
            int daysLate = date - sale.getDeadline();
            int daysEarly = -daysLate;
            switch (sale.getPeriod()) {
                case P1:
                    price = sale.getBaseValue() * 0.9;
                    break;
                case P2:
                    if (daysEarly >= 2)
                        price = sale.getBaseValue() * 0.95;
                    else
                        price = sale.getBaseValue();
                    break;
                case P3:
                    if (daysLate == 1)
                        price = sale.getBaseValue();
                    else
                        price = sale.getBaseValue() + 0.02 * sale.getBaseValue() * daysLate;
                    break;
                case P4:
                    price = sale.getBaseValue() + 0.05 * sale.getBaseValue() * daysLate;
            }
            return price;
        }

        public void removePoints(Client client, Sale sale) {
            if (sale.getPaymentDate() - sale.getDeadline() > 2) {
                client.setPoints(0.1 * client.getPoints());
                client.setStatus(ClientStatus.NORMAL);
            }
        }
    },
    ELITE {
        public void update(Client client) {
            if (client.getPoints() <= 25000) {
                client.setStatus(ClientStatus.SELECTION);
                client.updateStatus();
            }
        }

        public double salePrice(Sale sale, int date) {
            double price = 0;
            switch (sale.getPeriod()) {
                case P1:
                    price = sale.getBaseValue() * 0.9;
                    break;
                case P2:
                    price = sale.getBaseValue() * 0.9;
                    break;
                case P3:
                    price = sale.getBaseValue() * 0.95;
                    break;
                case P4:
                    price = sale.getBaseValue();
            }
            return price;
        }

        public void removePoints(Client client, Sale sale) {
            if (sale.getPaymentDate() - sale.getDeadline() > 15) {
                client.setPoints(0.25 * client.getPoints());
                client.setStatus(ClientStatus.SELECTION);
            }
        }
    };

    public abstract void update(Client client);
    public abstract double salePrice(Sale sale, int date);
    public abstract void removePoints(Client client, Sale sale);
}
