package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.Product;

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<Storefront> {

  public DoShowAllProducts(Storefront receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws DialogException {
    for (Product p : _receiver.getProducts())
      _display.addLine(p.toString());
    _display.display();
  }
}
