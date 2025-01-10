package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NoSuchClientKeyException;

/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {

  private Input<String> key;

  public DoShowClient(Storefront storefront) {
    super(Label.SHOW_CLIENT, storefront);
    key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      _display.add(_receiver.getClient(key.value()).toString());
      _display.addLine(_receiver.getClientNotifications(key.value()));
      _display.display();
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
