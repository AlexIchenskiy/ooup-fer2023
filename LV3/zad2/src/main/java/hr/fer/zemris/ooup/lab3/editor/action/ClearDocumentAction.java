package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ClearDocumentAction extends Action {

    private final TextEditorModel model;

    public ClearDocumentAction(TextEditorModel model) {
        super("Clear document");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        model.setLines(new ArrayList<>());
        model.getSelectionRange().reset();
        model.setCursorLocation(new Location(0, 0));
    }

}
