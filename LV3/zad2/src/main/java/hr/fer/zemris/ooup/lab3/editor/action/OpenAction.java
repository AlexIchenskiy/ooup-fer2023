package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenAction extends Action {

    private final TextEditor editor;

    public OpenAction(TextEditor editor) {
        super("Open");
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Open document");

            int selection = fileChooser.showOpenDialog(null);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();

                this.editor.getModel().setLines(Files.readAllLines(selected.toPath()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to open file.");
        }
    }

}
