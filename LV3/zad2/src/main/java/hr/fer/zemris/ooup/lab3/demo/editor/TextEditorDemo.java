package hr.fer.zemris.ooup.lab3.demo.editor;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.components.TextEditorFrame;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;

public class TextEditorDemo {

    private static final String defaultText = "Welcome to the text editor!\nStart writing here.\nEnjoy!";

    public static void main(String[] args) {
        new TextEditorFrame(new TextEditor(new TextEditorModel(defaultText)));
    }

}
