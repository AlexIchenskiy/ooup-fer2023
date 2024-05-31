package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.gui.render.SVGRendererImpl;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SVGExportAction extends AbstractAction {

    private GUI gui;

    public SVGExportAction(GUI gui) {
        this.gui = gui;
        this.putValue(Action.NAME, "SVG Export");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            SVGRendererImpl r = new SVGRendererImpl(file.toPath().toString(),
                    this.gui.getWidth(), this.gui.getHeight());

            for (GraphicalObject go : this.gui.getModel().list()) {
                go.render(r);
            }

            try {
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
