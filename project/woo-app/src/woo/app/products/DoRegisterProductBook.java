package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.NonUniqueProductKeyException;
import woo.exceptions.NoSuchSupplierKeyException;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  private Input<String> key;
  private Input<String> title;
  private Input<String> author;
  private Input<String> isbn;
  private Input<Integer> price;
  private Input<Integer> criticalLevel;
  private Input<String> supplier;

  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    key = _form.addStringInput(Message.requestProductKey());
    title = _form.addStringInput(Message.requestBookTitle());
    author = _form.addStringInput(Message.requestBookAuthor());
    isbn = _form.addStringInput(Message.requestISBN());
    price = _form.addIntegerInput(Message.requestPrice());
    criticalLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    supplier = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.addBook(key.value(), title.value(), author.value(), isbn.value(), price.value(), criticalLevel.value(), supplier.value());
    }
    catch (NonUniqueProductKeyException e) {
      throw new DuplicateProductKeyException(e.getKey());
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }
}
