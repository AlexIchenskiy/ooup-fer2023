package hr.fer.zemris.ooup.lab3.editor.plugins;

import hr.fer.zemris.ooup.lab3.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.singleton.UndoManager;

public interface Plugin {

    String getName(); // ime plugina (za izbornicku stavku)

    String getDescription(); // kratki opis

    void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack);

}
