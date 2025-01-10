package woo;

import java.io.*;
import woo.exceptions.*;
import java.util.Collection;
import java.util.Map;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /** Current save status. */
  private boolean saved = true;

  /**
   * @return the current date.
   */
  public int getDate() {
    return _store.getDate();
  }

  /**
   * Advance the current date by a given number of days.
   * 
   * @param days number of days to advance
   */
  public void advanceDate(int days) throws BadDateException {
    _store.advanceDate(days);
    saved = false;
  }

  public int getAvailableBalance() {
    return _store.getAvailableBalance();
  }

  public int getAccountingBalance() {
    return _store.getAccountingBalance();
  }

  public void addBox(String key, int price, int criticalLevel, String supplierKey, String type) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException {
    _store.addBox(key, price, criticalLevel, supplierKey, type);
    saved = false;
  }

  public void addContainer(String key, int price, int criticalLevel, String supplierKey, String type, String level) throws NonUniqueProductKeyException, NoSuchSupplierKeyException, NoSuchServiceTypeException, NoSuchServiceLevelException {
    _store.addContainer(key, price, criticalLevel, supplierKey, type, level);
    saved = false;
  }

  public void addBook(String key, String title, String author, String isbn, int price, int criticalLevel, String supplierKey) throws NonUniqueProductKeyException, NoSuchSupplierKeyException {
    _store.addBook(key, title, author, isbn, price, criticalLevel, supplierKey);
    saved = false;
  }

  public void changeProductPrice(String key, int price) throws NoSuchProductKeyException {
    _store.changeProductPrice(key, price);
    saved = false;
  }

  /**
   * @return a collection with all the products.
   */
  public Collection<Product> getProducts() {
    return _store.getProducts();
  }

  public Collection<Product> getProductsUnderPrice(int price) {
    return _store.getProductsUnderPrice(price);
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
    _store.addClient(key, name, address);
    saved = false;
  }

  public boolean toggleProductNotifications(String clientKey, String productKey) throws NoSuchClientKeyException, NoSuchProductKeyException {
    saved = false;
    return _store.toggleProductNotifications(clientKey, productKey);
  }

  /**
   * Get the client with the given identifier.
   * 
   * @param key the client's identifier.
   * @return the client
   * @throws NoSuchClientKeyException
   */
  public Client getClient(String key) throws NoSuchClientKeyException {
    return _store.getClient(key);
  }

  /**
   * @return a collection with all the clients.
   */
  public Collection<Client> getClients() {
    return _store.getClients();
  }

  public String getClientNotifications(String key) throws NoSuchClientKeyException {
    saved = false;
    return _store.getClientNotifications(key);
  }

  public Collection<Sale> getClientTransactions(String key) throws NoSuchClientKeyException {
    return _store.getClientTransactions(key);
  }

  public Collection<Sale> getClientPayments(String key) throws NoSuchClientKeyException {
    return _store.getClientPayments(key);
  }

  public void addSupplier(String key, String name, String address) throws NonUniqueSupplierKeyException {
    _store.addSupplier(key, name, address);
    saved = false;
  }

  public boolean toggleSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    saved = false;
    return _store.toggleSupplierTransactions(key);
  }

  /**
   * @return a collection with all the suppliers.
   */
  public Collection<Supplier> getSuppliers() {
    return _store.getSuppliers();
  }

  public Collection<Order> getSupplierTransactions(String key) throws NoSuchSupplierKeyException {
    return _store.getSupplierTransactions(key);
  }

  public void addSale(String clientKey, int deadline, String productKey, int amount) throws NoSuchClientKeyException, NoSuchProductKeyException, NotEnoughProductException {
    _store.addSale(clientKey, deadline, productKey, amount);
    saved = false;
  }

  public void addOrder(String supplierKey, Map<String, Integer> productsOrdered) throws NoSuchSupplierKeyException, NonAuthorizedSupplierException, NoSuchProductKeyException, IncorrectSupplierException {
    _store.addOrder(supplierKey, productsOrdered);
    saved = false;
  }

  public void payTransaction(int key) throws NoSuchTransactionKeyException {
    _store.payTransaction(key);
    saved = false;
  }

  public Transaction getTransaction(int key) throws NoSuchTransactionKeyException {
    return _store.getTransaction(key);
  }

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename.equals(""))
      throw new MissingFileAssociationException();
    else if (!saved) {
      ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
      out.writeObject(_store);
      out.close();
      saved = true;
    }
  }

  /**
   * @param filename
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void saveAs(String filename) throws IOException, FileNotFoundException, MissingFileAssociationException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try {
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
      _store = (Store) in.readObject();
      in.close();
      _filename = filename;
    }
    catch (IOException | ClassNotFoundException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
      saved = false;
    }
    catch (IOException | BadEntryException | NonUniqueSupplierKeyException | NonUniqueClientKeyException
        | NonUniqueProductKeyException | NoSuchSupplierKeyException | NoSuchServiceTypeException
        | NoSuchServiceLevelException e) {
      throw new ImportFileException(textfile);
    }
  }

}
