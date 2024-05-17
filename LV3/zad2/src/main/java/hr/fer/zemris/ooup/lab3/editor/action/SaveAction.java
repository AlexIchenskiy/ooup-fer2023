package hr.fer.zemris.ooup.lab3.editor.action;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveAction extends Action {

    private final TextEditor editor;

    public SaveAction(TextEditor editor) {
        super("Save");
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!editor.getModel().getText().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save as");

            int selection = fileChooser.showSaveDialog(null);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Path path = file.toPath();

                if (path.toFile().exists()) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "This file already exists. Overwrite it?");

                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }

                    this.saveFile(path.toString(), this.editor.getModel().getLines());
                    return;
                }

                this.saveFile(path.toString(), this.editor.getModel().getLines());
            }
        }
    }

    private boolean saveFile(String path, List<String> lines) {
        try {
            Path savePath = Paths.get(path);

            if (Files.exists(savePath)) Files.delete(savePath);

            Files.write(savePath, lines);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
