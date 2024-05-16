package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.observer.ClipboardObserver;

import java.awt.event.ActionEvent;

public class PasteAction extends Action implements ClipboardObserver {

    private final TextEditor editor;

    public PasteAction(TextEditor editor) {
        super("Paste");
        this.editor = editor;
        this.editor.getClipboardStack().addClipboardListener(this);
        this.setEnabled(!this.editor.getClipboardStack().isEmpty());
    }

    @Override
    public void updateClipboard() {
        this.setEnabled(!this.editor.getClipboardStack().isEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.editor.getModel().insert(this.editor.getClipboardStack().peek());
    }

}
