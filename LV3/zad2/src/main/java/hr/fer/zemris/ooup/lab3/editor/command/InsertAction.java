package hr.fer.zemris.ooup.lab3.editor.command;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.LocationRange;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class InsertAction implements EditAction {

    private final TextEditorModel model;

    private final Location prevCursorLocation;
    private final LocationRange prevSelectionRange;
    private Character c = null;
    private String text = null;

    private final List<String> prevLines;

    public InsertAction(TextEditorModel model, char c) {
        this.model = model;
        this.prevCursorLocation = this.model.getCursorLocation();
        this.prevSelectionRange = this.model.getSelectionRange();
        this.c = c;
        this.prevLines = new ArrayList<>(this.model.getLines());
    }

    public InsertAction(TextEditorModel model, String text) {
        this.model = model;
        this.prevCursorLocation = this.model.getCursorLocation();
        this.prevSelectionRange = this.model.getSelectionRange();
        this.text = text;
        this.prevLines = new ArrayList<>(this.model.getLines());
    }

    @Override
    public void executeDo() {
        if (this.c == null) {
            this.model.insertPerform(this.text);
            return;
        }

        this.model.insertPerform(this.c);
    }

    @Override
    public void executeUndo() {
        this.model.setLines(this.prevLines);
        this.model.setCursorLocation(this.prevCursorLocation);
        this.model.setSelectionRange(this.prevSelectionRange);
    }
}
