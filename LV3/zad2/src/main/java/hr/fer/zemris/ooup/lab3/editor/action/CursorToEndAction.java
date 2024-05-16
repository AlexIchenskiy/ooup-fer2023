package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.awt.event.ActionEvent;

public class CursorToEndAction extends Action {

    private final TextEditorModel model;

    public CursorToEndAction(TextEditorModel model) {
        super("Cursor to end");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int numOfLines = this.model.getLines().size();
        this.model.setCursorLocation(new Location(numOfLines - 1,
                this.model.getLines().get(numOfLines - 1).length()));
    }

}
