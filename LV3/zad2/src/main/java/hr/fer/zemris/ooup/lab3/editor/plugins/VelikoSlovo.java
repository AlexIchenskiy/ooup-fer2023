package hr.fer.zemris.ooup.lab3.editor.plugins;

import hr.fer.zemris.ooup.lab3.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.singleton.UndoManager;

import java.util.ArrayList;
import java.util.List;

public class VelikoSlovo implements Plugin {

    @Override
    public String getName() {
        return "Capitalize";
    }

    @Override
    public String getDescription() {
        return "Capitalizes every word in the current file.";
    }

    @Override
    public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
        List<String> newLines = new ArrayList<>();

        for (int i = 0; i < model.getLines().size(); i++) {
            newLines.add(capitalizeLine(model.getLines().get(i)));
        }

        model.setLines(newLines);
    }

    private String capitalizeLine(String line) {
        StringBuilder sb = new StringBuilder();
        boolean capitalize = true;

        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if (Character.isWhitespace(currChar)) {
                sb.append(currChar);
                capitalize = true;
                continue;
            }

            if (Character.isLetter(currChar)) {
                if (capitalize) {
                    sb.append(Character.toUpperCase(currChar));
                    capitalize = false;
                    continue;
                }
            }

            sb.append(currChar);
        }

        return sb.toString();
    }

}
