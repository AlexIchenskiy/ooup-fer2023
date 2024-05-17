package hr.fer.zemris.ooup.lab3.editor.plugins;

import hr.fer.zemris.ooup.lab3.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.singleton.UndoManager;

import javax.swing.*;
import java.util.List;

public class Statistika implements Plugin {

    @Override
    public String getName() {
        return "Statistics";
    }

    @Override
    public String getDescription() {
        return "Shows the number of lines, words and letters in the document.";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        JOptionPane.showMessageDialog(null, "Statistics for the current file.\n" +
                " - Number of lines: " + model.getLines().size() + "\n - Number of words: " +
                this.getNumberOfWords(model.getLines()) + "\n - Number of letters: " +
                this.getNumberOfLetters(model.getLines()));
    }

    private int getNumberOfWords(List<String> lines) {
        int number = 0;

        for (String line: lines) {
            String[] words = line.split("\\s+");
            number += words.length;
        }

        return number;
    }

    private int getNumberOfLetters(List<String> lines) {
        int number = 0;

        for (String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                if (Character.isLetter(line.charAt(i))) number++;
            }
        }

        return number;
    }

}
