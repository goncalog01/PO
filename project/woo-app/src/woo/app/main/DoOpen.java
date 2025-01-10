package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.FileOpenFailedException;
import woo.exceptions.UnavailableFileException;

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<Storefront> {

  private Input<String> filename;

  /** @param receiver */
  public DoOpen(Storefront receiver) {
    super(Label.OPEN, receiver);
    filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.load(filename.value());
    }
    catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    }
  }
}
