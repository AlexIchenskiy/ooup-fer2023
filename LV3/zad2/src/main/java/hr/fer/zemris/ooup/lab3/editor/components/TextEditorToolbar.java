package hr.fer.zemris.ooup.lab3.editor.components;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.action.*;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

import javax.swing.*;

public class TextEditorToolbar extends JToolBar {

    private final TextEditor editor;

    public TextEditorToolbar(TextEditor editor) {
        this.editor = editor;
        this.initGUI();
    }

    private void initGUI() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new OpenAction());
        fileMenu.add(new SaveAction());
        fileMenu.add(new ExitAction());

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new UndoAction(this.editor.getModel()));
        editMenu.add(new RedoAction(this.editor.getModel()));
        editMenu.add(new CutAction(this.editor));
        editMenu.add(new CopyAction(this.editor));
        editMenu.add(new PasteAction(this.editor));
        editMenu.add(new PasteAndTakeAction(this.editor));
        editMenu.add(new DeleteSelectionAction(this.editor.getModel()));
        editMenu.add(new ClearDocumentAction(this.editor.getModel()));

        JMenu moveMenu = new JMenu("Move");
        moveMenu.add(new CursorToStartAction(this.editor.getModel()));
        moveMenu.add(new CursorToEndAction(this.editor.getModel()));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(moveMenu);

        this.add(menuBar);
    }

}