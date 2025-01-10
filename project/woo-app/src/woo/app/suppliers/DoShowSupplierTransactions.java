package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.Order;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.NoSuchSupplierKeyException;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  Input<String> key;

  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    key = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      for (Order o : _receiver.getSupplierTransactions(key.value()))
        _display.add(o.toString());
      _display.display();
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }

}
