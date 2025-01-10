package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.DuplicateProductKeyException;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownServiceLevelException;
import woo.exceptions.NonUniqueProductKeyException;
import woo.exceptions.NoSuchSupplierKeyException;
import woo.exceptions.NoSuchServiceTypeException;
import woo.exceptions.NoSuchServiceLevelException;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  private Input<String> key;
  private Input<Integer> price;
  private Input<Integer> criticalLevel;
  private Input<String> supplier;
  private Input<String> type;
  private Input<String> level;

  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    key = _form.addStringInput(Message.requestProductKey());
    price = _form.addIntegerInput(Message.requestPrice());
    criticalLevel = _form.addIntegerInput(Message.requestStockCriticalValue());
    supplier = _form.addStringInput(Message.requestSupplierKey());
    type = _form.addStringInput(Message.requestServiceType());
    level = _form.addStringInput(Message.requestServiceLevel());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.addContainer(key.value(), price.value(), criticalLevel.value(), supplier.value(), type.value(), level.value());
    }
    catch (NonUniqueProductKeyException e) {
      throw new DuplicateProductKeyException(e.getKey());
    }
    catch (NoSuchSupplierKeyException e) {
      throw new UnknownSupplierKeyException(e.getKey());
    }
    catch (NoSuchServiceTypeException e) {
      throw new UnknownServiceTypeException(e.getType());
    }
    catch (NoSuchServiceLevelException e) {
      throw new UnknownServiceLevelException(e.getLevel());
    }
  }
}
