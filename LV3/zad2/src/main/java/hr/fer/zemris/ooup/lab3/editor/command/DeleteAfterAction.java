package hr.fer.zemris.ooup.lab3.editor.command;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.LocationRange;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteAfterAction implements EditAction {

    private final TextEditorModel model;

    private final Location prevCursorLocation;
    private final LocationRange prevSelectionRange;

    private final List<String> prevLines;

    public DeleteAfterAction(TextEditorModel model) {
        this.model = model;
        this.prevCursorLocation = this.model.getCursorLocation();
        this.prevSelectionRange = this.model.getSelectionRange();
        this.prevLines = new ArrayList<>(this.model.getLines());
    }

    @Override
    public void executeDo() {
        this.model.deleteAfterExecute();
    }

    @Override
    public void executeUndo() {
        this.model.setLines(this.prevLines);
        this.model.setCursorLocation(this.prevCursorLocation);
        this.model.setSelectionRange(this.prevSelectionRange);
    }

}
