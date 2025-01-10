package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnavailableProductException;
import woo.exceptions.NoSuchClientKeyException;
import woo.exceptions.NoSuchProductKeyException;
import woo.exceptions.NotEnoughProductException;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  private Input<String> client;
  private Input<Integer> deadline;
  private Input<String> product;
  private Input<Integer> amount;

  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    client = _form.addStringInput(Message.requestClientKey());
    deadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    product = _form.addStringInput(Message.requestProductKey());
    amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.addSale(client.value(), deadline.value(), product.value(), amount.value());
    }
    catch (NoSuchClientKeyException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
    catch (NoSuchProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
    catch (NotEnoughProductException e) {
      throw new UnavailableProductException(e.getKey(), e.getRequested(), e.getAvailable());
    }
  }

}
