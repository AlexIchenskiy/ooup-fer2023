package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.observer.SelectionObserver;

import java.awt.event.ActionEvent;

public class CopyAction extends Action implements SelectionObserver {

    private final TextEditor editor;

    public CopyAction(TextEditor editor) {
        super("Copy");
        this.editor = editor;
        this.editor.getModel().addSelectionListener(this);
        this.setEnabled(!this.editor.getModel().getSelectionRange().isEmpty());
    }

    @Override
    public void updateSelection() {
        this.setEnabled(!this.editor.getModel().getSelectionRange().isEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.editor.getClipboardStack().push(
                this.editor.getModel().getText(this.editor.getModel().getSelectionRange()));
    }

}
