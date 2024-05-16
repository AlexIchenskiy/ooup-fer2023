package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.awt.event.ActionEvent;

public class DeleteSelectionAction extends Action {

    private final TextEditorModel model;

    public DeleteSelectionAction(TextEditorModel model) {
        super("Delete selection");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.model.deleteRange(this.model.getSelectionRange());
    }

}
