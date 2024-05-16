package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.observer.UndoObserver;

import java.awt.event.ActionEvent;

public class UndoAction extends Action implements UndoObserver {

    private final TextEditorModel model;

    public UndoAction(TextEditorModel model) {
        super("Undo");
        this.model = model;
        this.model.getManager().addUndoListener(this);
        this.setEnabled(!this.model.getManager().isUndoEmpty());
    }

    @Override
    public void updateUndo() {
        this.setEnabled(!this.model.getManager().isUndoEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.model.getManager().undo();
    }

}
