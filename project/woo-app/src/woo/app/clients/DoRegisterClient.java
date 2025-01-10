package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateClientKeyException;
import woo.exceptions.NonUniqueClientKeyException;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<Storefront> {

  private Input<String> key;
  private Input<String> name;
  private Input<String> address;

  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    key = _form.addStringInput(Message.requestClientKey());
    name = _form.addStringInput(Message.requestClientName());
    address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.addClient(key.value(), name.value(), address.value());
    }
    catch (NonUniqueClientKeyException e) {
      throw new DuplicateClientKeyException(e.getKey());
    }
  }
}
