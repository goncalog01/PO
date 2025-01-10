package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Sale;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NoSuchClientKeyException;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  Input<String> key;

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      for (Sale s : _receiver.getClientTransactions(key.value()))
        _display.addLine(s.toString());
      _display.display();
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }

}
