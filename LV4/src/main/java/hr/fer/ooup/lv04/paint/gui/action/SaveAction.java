package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveAction extends AbstractAction {

    private GUI gui;

    public SaveAction(GUI gui) {
        this.gui = gui;

        this.putValue(Action.NAME, "Pohrani");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            List<String> lines = new ArrayList<>();

            for (GraphicalObject go : this.gui.getModel().list()) go.save(lines);

            try {
                Files.write(file.toPath(), lines);
            } catch (Exception ignored) {}
        }
    }

}
