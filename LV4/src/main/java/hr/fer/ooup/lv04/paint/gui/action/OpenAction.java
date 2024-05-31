package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.shape.CompositeShape;
import hr.fer.ooup.lv04.paint.model.shape.LineSegment;
import hr.fer.ooup.lv04.paint.model.shape.Oval;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class OpenAction extends AbstractAction {

    private final Map<String, GraphicalObject> prototypeById = Map.of(
            "@LINE", new LineSegment(),
            "@OVAL", new Oval(),
            "@COMP", new CompositeShape(new ArrayList<>())
    );

    private GUI gui;

    public OpenAction(GUI gui) {
        this.gui = gui;
        this.putValue(Action.NAME, "Ucitaj");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                List<String> lines = Files.readAllLines(file.toPath());

                Stack<GraphicalObject> gos = new Stack<>();

                for (String line : lines) {
                    String[] parts = line.split(" ", 2);
                    prototypeById.get(parts[0].trim()).load(gos, parts[1].trim());
                }

                this.gui.getModel().clear();

                while (!gos.isEmpty()) {
                    this.gui.getModel().addGraphicalObject(gos.pop());
                }
            } catch (Exception ignored) {
            }
        }
    }

}
