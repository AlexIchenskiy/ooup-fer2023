package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.awt.event.ActionEvent;

public class CursorToStartAction extends Action {

    private final TextEditorModel model;

    public CursorToStartAction(TextEditorModel model) {
        super("Cursor to start");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.model.setCursorLocation(new Location(0, 0));
    }

}
