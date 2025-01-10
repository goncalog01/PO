package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Sale;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NoSuchClientKeyException;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  Input<String> key;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      for (Sale s : _receiver.getClientPayments(key.value()))
        _display.addLine(s.toString());
      _display.display();
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }

}
