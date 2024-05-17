package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends Action {

    private final TextEditor editor;

    public ExitAction(TextEditor editor) {
        super("Exit");
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SwingUtilities.getWindowAncestor(this.editor).dispose();
    }

}
