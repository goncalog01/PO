package woo;

import java.io.*;
import woo.exceptions.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Current date. */
  private int date = 0;

  private int nextTransactionKey = 0;

  /** Store clients. */
  private Map<String, Client> clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);

  /** Store products. */
  private Map<String, Product> products = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);

  /** Store suppliers. */
  private Map<String, Supplier> suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);

  private Map<Integer, Transaction> transactions = new TreeMap<Integer, Transaction>();

  /**
   * @return the current date.
   */
  public int getDate() {
    return date;
  }

  /**
   * Advance the current date by a given number of days.
   * 
   * @param days number of days to advance
   */
  public void advanceDate(int days) throws BadDateException {
    if (days > 0) {
      date += days;
      updateTransactions();
    }
    else
      throw new BadDateException(days);
  }

  public int getAvailableBalance() {
    double available = 0;

    for (Transaction transaction : transactions.values())
      if (transaction.wasPaid())
        available += transaction.getBalanceValue();

    return (int)Math.round(available);
  }

  public int getAccountingBalance() {
    double accounting = 0;

    for (Transaction transaction : transactions.values())
      accounting += transaction.getBalanceValue();

    return (int)Math.round(accounting);
  }

  public void addBox(String key, int price, int criticalLevel, String supplierKey, String type) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException {
    if (products.containsKey(key))
      throw new NonUniqueProductKeyException(key);
    else {
      Supplier supplier = getSupplier(supplierKey);
      Box box = new Box(key, supplier, price, criticalLevel, type);
      products.put(key, box);
      for (Client c : clients.values())
        box.registerObserver(c.getKey(), c);
    }
  }

  public void addContainer(String key, int price, int criticalLevel, String supplierKey, String type, String level) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException, NoSuchServiceLevelException {
    if (products.containsKey(key))
      throw new NonUniqueProductKeyException(key);
    else {
      Supplier supplier = getSupplier(supplierKey);
      Container container = new Container(key, supplier, price, criticalLevel, type, level);
      products.put(key, container);
      for (Client c : clients.values())
        container.registerObserver(c.getKey(), c);
    }
  }

  public void addBook(String key, String title, String author, String isbn, int price, int criticalLevel, String supplierKey) throws NonUniqueProductKeyException, NoSuchSupplierKeyException {
    if (products.containsKey(key))
      throw new NonUniqueProductKeyException(key);
    else {
      Supplier supplier = getSupplier(supplierKey);
      Book book = new Book(key, supplier, price, criticalLevel, title, author, isbn);
      products.put(key, book);
      for (Client c : clients.values())
        book.registerObserver(c.getKey(), c);
    }
  }

  public void changeProductPrice(String key, int price) throws NoSuchProductKeyException {
    if (!products.containsKey(key))
      throw new NoSuchProductKeyException(key);
    else
      products.get(key).setPrice(price);
  }

  public Product getProduct(String key) throws NoSuchProductKeyException {
    if (products.containsKey(key))
      return products.get(key);
    else
      throw new NoSuchProductKeyException(key);
  }

  /**
   * Return all the products as an unmodifiable collection.
   * 
   * @return a collection with all the products.
   */
  public Collection<Product> getProducts() {
    return Collections.unmodifiableCollection(products.values());
  }

  public Collection<Product> getProductsUnderPrice(int price) {
    List<Product> prods = new ArrayList<Product>();

    for (Product p : products.values())
      if (p.getPrice() < price)
        prods.add(p);

    return Collections.unmodifiableCollection(prods);
  }

  /**
   * Create and register a new client.
   * 
   * @param key     the client's identifier.
   * @param name    the client's name.
   * @param address the client's address.
   * @throws NonUniqueClientKeyException
   */
  public void addClient(String key, String name, String address) throws NonUniqueClientKeyException {
    if (clients.containsKey(key))
      throw new NonUniqueClientKeyException(key);
    else {
      Client client = new Client(key, name, address);
      clients.put(key, client);
      for (Product p : products.values())
        p.registerObserver(key, client);
    }
  }

  public boolean toggleProductNotifications(String clientKey, String productKey) throws NoSuchClientKeyException, NoSuchProductKeyException {
    Client client = getClient(clientKey);
    Product product = getProduct(productKey);
    if (product.observedBy(clientKey)) {
      product.removeObserver(clientKey);
      return false;
    }
    else {
      product.registerObserver(clientKey, client);
      return true;
    }
  }

  /**
   * Get the client with the given identifier.
   * 
   * @param key the client's identifier.
   * @return the client
   * @throws NoSuchClientKeyException
   */
  public Client getClient(String key) throws NoSuchClientKeyException {
    if (clients.containsKey(key))
      return clients.get(key);
    else
      throw new NoSuchClientKeyException(key);
  }

  /**
   * Return all the clients as an unmodifiable collection.
   * 
   * @return a collection with all the clients.
   */
  public Collection<Client> getClients() {
    return Collections.unmodifiableCollection(clients.values());
  }

  public String getClientNotifications(String key) throws NoSuchClientKeyException {
    return getClient(key).getNotifications();
  }

  public Collection<Sale> getClientTransactions(String key) throws NoSuchClientKeyException {
    return Collections.unmodifiableCollection(getClient(key).getTransactions());
  }

  public Collection<Sale> getClientPayments(String key) throws NoSuchClientKeyException {
    return Collections.unmodifiableCollection(getClient(key).getPayments());
  }


  public void addSupplier(String key, String name, String address) throws NonUniqueSupplierKeyException {
    if (suppliers.containsKey(key))
      throw new NonUniqueSupplierKeyException(key);
    else {
      Supplier supplier = new Supplier(key, name, address);
      suppliers.put(key, supplier);
    }
  }

  public boolean toggleSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    return getSupplier(key).toggleTransactions();
  }

  public Supplier getSupplier(String key) throws NoSuchSupplierKeyException {
    if (suppliers.containsKey(key))
      return suppliers.get(key);
    else
      throw new NoSuchSupplierKeyException(key);
  }

  /**
   * Return all the suppliers as an unmodifiable collection.
   * 
   * @return a collection with all the suppliers.
   */
  public Collection<Supplier> getSuppliers() {
    return Collections.unmodifiableCollection(suppliers.values());
  }

  public Collection<Order> getSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    return Collections.unmodifiableCollection(getSupplier(key).getTransactions());
  }

  public void addSale(String clientKey, int deadline, String productKey, int amount) throws NoSuchClientKeyException, NoSuchProductKeyException, NotEnoughProductException {
    if (deadline >= 0) {
      Client client = getClient(clientKey);
      Product product = getProduct(productKey);
      product.removeStock(amount);
      Sale sale = new Sale(nextTransactionKey, client, deadline, product, amount, getDate());
      transactions.put(nextTransactionKey++, sale);
    }
  }

  public void addOrder(String supplierKey, Map<String, Integer> productsOrdered) throws NoSuchSupplierKeyException, NonAuthorizedSupplierException, NoSuchProductKeyException, IncorrectSupplierException {
    Supplier supplier = getSupplier(supplierKey);
    if (!supplier.isActive())
      throw new NonAuthorizedSupplierException(supplierKey);

    Product product;
    for (String key : productsOrdered.keySet()) {
      product = getProduct(key);
      if (!product.getSupplier().equals(supplier))
        throw new IncorrectSupplierException(supplier.getKey(), product.getKey());
    }

    Map<Product, Integer> prods = new LinkedHashMap<Product, Integer>();
    int value = 0;
    for (Map.Entry<String, Integer> entry : productsOrdered.entrySet()) {
      product = getProduct(entry.getKey());
      int amount = entry.getValue();
      product.addStock(amount);
      value += product.getPrice() * amount;
      prods.put(product, amount);
    }

    Order order = new Order(nextTransactionKey, supplier, prods, value);
    order.pay(getDate());
    transactions.put(nextTransactionKey++, order);
  }

  public void payTransaction(int key) throws NoSuchTransactionKeyException {
    Transaction transaction = getTransaction(key);

    if (!transaction.wasPaid())
      transaction.pay(getDate());
  }

  public Transaction getTransaction(int key) throws NoSuchTransactionKeyException {
    if (transactions.containsKey(key))
      return transactions.get(key);
    else
      throw new NoSuchTransactionKeyException(key);
  }

  public void updateTransactions() {
    for (Transaction transaction : transactions.values())
      if (!transaction.wasPaid())
        transaction.update(getDate());
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   * @throws NonUniqueSupplierKeyException
   * @throws NonUniqueClientKeyException
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   * @throws NoSuchServiceLevelException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, NonUniqueSupplierKeyException,
      NonUniqueClientKeyException, NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException,
      NoSuchServiceLevelException {
    BufferedReader reader = new BufferedReader(new FileReader(txtfile));
    String line;
    while ((line = reader.readLine()) != null) {
      String[] fields = line.split("\\|");
      registerFromFields(fields);
    }
    reader.close();
  }

  /**
   * Create and register an object given its fields.
   * 
   * @param fields the object's fields.
   * @throws BadEntryException
   * @throws NonUniqueSupplierKeyException
   * @throws NonUniqueClientKeyException
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   * @throws NoSuchServiceLevelException
   */
  void registerFromFields(String[] fields) throws BadEntryException, NonUniqueSupplierKeyException,
      NonUniqueClientKeyException, NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException,
      NoSuchServiceLevelException {
    switch (fields[0]) {
      case "SUPPLIER":
        registerSupplier(fields);
        break;
      case "CLIENT":
        registerClient(fields);
        break;
      case "BOX":
        registerBox(fields);
        break;
      case "CONTAINER":
        registerContainer(fields);
        break;
      case "BOOK":
        registerBook(fields);
        break;
      default:
        throw new BadEntryException(fields[0]);
    }
  }

  /**
   * Create and register a box given its fields.
   * 
   * @param fields the box's fields.
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   */
  void registerBox(String... fields) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException {
    if (products.containsKey(fields[1]))
      throw new NonUniqueProductKeyException(fields[1]);
    else {
      Supplier supplier = getSupplier(fields[3]);
      int price = Integer.parseInt(fields[4]);
      int criticalLevel = Integer.parseInt(fields[5]);
      int stock = Integer.parseInt(fields[6]);
      Box box = new Box(fields[1], supplier, price, criticalLevel, stock, fields[2]);
      products.put(fields[1], box);
      for (Client c : clients.values())
        box.registerObserver(c.getKey(), c);
    }
  }

  /**
   * Create a register a container given its fields.
   * 
   * @param fields the container's fields.
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   * @throws NoSuchServiceTypeException
   * @throws NoSuchServiceLevelException
   */
  void registerContainer(String... fields) throws NonUniqueProductKeyException, NoSuchSupplierKeyException,
      NoSuchServiceTypeException, NoSuchServiceLevelException {
    if (products.containsKey(fields[1]))
      throw new NonUniqueProductKeyException(fields[1]);
    else {
      Supplier supplier = getSupplier(fields[4]);
      int price = Integer.parseInt(fields[5]);
      int criticalLevel = Integer.parseInt(fields[6]);
      int stock = Integer.parseInt(fields[7]);
      Container container = new Container(fields[1], supplier, price, criticalLevel, stock, fields[2], fields[3]);
      products.put(fields[1], container);
      for (Client c : clients.values())
        container.registerObserver(c.getKey(), c);
    }
  }

  /**
   * Create and register a book given its fields.
   * 
   * @param fields the book's fields.
   * @throws NonUniqueProductKeyException
   * @throws NoSuchSupplierKeyException
   */
  void registerBook(String... fields) throws NonUniqueProductKeyException, NoSuchSupplierKeyException {
    if (products.containsKey(fields[1]))
      throw new NonUniqueProductKeyException(fields[1]);
    else {
      Supplier supplier = getSupplier(fields[5]);
      int price = Integer.parseInt(fields[6]);
      int criticalLevel = Integer.parseInt(fields[7]);
      int stock = Integer.parseInt(fields[8]);
      Book book = new Book(fields[1], supplier, price, criticalLevel, stock, fields[2], fields[3], fields[4]);
      products.put(fields[1], book);
      for (Client c : clients.values())
        book.registerObserver(c.getKey(), c);
    }
  }

  /**
   * Create and register a client given its fields.
   * 
   * @param fields the client's fields.
   * @throws NonUniqueClientKeyException
   */
  void registerClient(String... fields) throws NonUniqueClientKeyException {
    if (clients.containsKey(fields[1]))
      throw new NonUniqueClientKeyException(fields[1]);
    else {
      Client client = new Client(fields[1], fields[2], fields[3]);
      clients.put(fields[1], client);
      for (Product p : products.values())
        p.registerObserver(fields[1], client);
    }
  }

  /**
   * Create and register a supplier given its fields.
   * 
   * @param fields the supplier's fields.
   * @throws NonUniqueSupplierKeyException
   */
  void registerSupplier(String... fields) throws NonUniqueSupplierKeyException {
    if (suppliers.containsKey(fields[1]))
      throw new NonUniqueSupplierKeyException(fields[1]);
    else {
      Supplier supplier = new Supplier(fields[1], fields[2], fields[3]);
      suppliers.put(fields[1], supplier);
    }
  }
}