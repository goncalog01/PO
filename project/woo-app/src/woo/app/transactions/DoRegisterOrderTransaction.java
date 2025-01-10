package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Form;
import woo.Storefront;
import java.util.Map;
import java.util.LinkedHashMap;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.WrongSupplierException;
import woo.exceptions.NoSuchSupplierKeyException;
import woo.exceptions.NonAuthorizedSupplierException;
import woo.exceptions.NoSuchProductKeyException;
import woo.exceptions.IncorrectSupplierException;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  private Input<String> supplier;
  private Input<String> product;
  private Input<Integer> amount;
  private Input<Boolean> more;
  private Form _requestMoreForm = new Form(Label.REGISTER_ORDER_TRANSACTION);

  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    supplier = _form.addStringInput(Message.requestSupplierKey());
    product = _requestMoreForm.addStringInput(Message.requestProductKey());
    amount = _requestMoreForm.addIntegerInput(Message.requestAmount());
    more = _requestMoreForm.addBooleanInput(Message.requestMore());
  }

  @Override
  public final void execute() throws DialogException {
    Map<String, Integer> products = new LinkedHashMap<String, Integer>();

    _form.parse();
    _requestMoreForm.parse();
    products.put(product.value(), amount.value());

    while (more.value()) {
      _requestMoreForm.parse();
      if (products.containsKey(product.value())) {
        int newAmount = products.get(product.value()) + amount.value();
        products.put(product.value(), newAmount);
      }
      else
        products.put(product.value(), amount.value());
    }
        
    try {
      _receiver.addOrder(supplier.value(), products);
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
    catch (NonAuthorizedSupplierException e) {
      throw new UnauthorizedSupplierException(e.getKey());
    }
    catch (NoSuchProductKeyException e) {
      throw new UnknownProductKeyException(e.getKey());
    }
    catch (IncorrectSupplierException e) {
      throw new WrongSupplierException(e.getSupplierKey(), e.getProductKey());
    }
  }

}
