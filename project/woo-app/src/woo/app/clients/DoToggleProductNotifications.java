package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.NoSuchClientKeyException;
import woo.exceptions.NoSuchProductKeyException;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  Input<String> clientKey;
  Input<String> productKey;

  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    clientKey = _form.addStringInput(Message.requestClientKey());
    productKey = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      if (_receiver.toggleProductNotifications(clientKey.value(), productKey.value()))
        _display.popup(Message.notificationsOn(clientKey.value(), productKey.value()));
      else
      _display.popup(Message.notificationsOff(clientKey.value(), productKey.value()));
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
    catch (NoSuchProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
  }

}
