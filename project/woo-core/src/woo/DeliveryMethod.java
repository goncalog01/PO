package woo;

public enum DeliveryMethod {
    APP {
        public void processNotification(Client client, Notification notification) {
            client.addNotification(notification);
        }
    };

    public abstract void processNotification(Client client, Notification notification);
}
