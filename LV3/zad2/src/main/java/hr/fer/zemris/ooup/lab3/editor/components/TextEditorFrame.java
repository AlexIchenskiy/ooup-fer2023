package hr.fer.zemris.ooup.lab3.editor.components;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.components.TextEditorToolbar;

import javax.swing.*;
import java.awt.*;

public class TextEditorFrame extends JFrame {

    private final String title = "Text editor";

    public TextEditorFrame(TextEditor editor) throws HeadlessException {
        this.initGUI(editor);
    }

    private void initGUI(TextEditor editor) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        this.setSize(600, 600);
        this.setPreferredSize(new Dimension(600, 600));
        this.setTitle(title);
        this.setLayout(new BorderLayout());

        this.add(editor, BorderLayout.CENTER);
        this.add(new TextEditorToolbar(editor), BorderLayout.NORTH);
        this.add(new TextEditorStatusBar(editor.getModel()), BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

}
