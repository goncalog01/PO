package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.NoSuchSupplierKeyException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  Input<String> key;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    key = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      if (_receiver.toggleSupplierTransactions(key.value()))
        _display.popup(Message.transactionsOn(key.value()));
      else
        _display.popup(Message.transactionsOff(key.value()));
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
  }

}
