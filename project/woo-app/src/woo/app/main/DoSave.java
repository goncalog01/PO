package woo.app.main;

import pt.tecnico.po.ui.Command;
import java.io.IOException;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.exceptions.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  private Input<String> filename;

  /** @param receiver */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
    filename = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try {
      _receiver.save();
    }
    catch (MissingFileAssociationException e) {
      _form.parse();
      try {
        _receiver.saveAs(filename.value());
      }
      catch (IOException | MissingFileAssociationException ex) {
        ex.printStackTrace();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
