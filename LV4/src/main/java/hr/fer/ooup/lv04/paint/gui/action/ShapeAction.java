package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;
import hr.fer.ooup.lv04.paint.state.AddShapeState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShapeAction extends AbstractAction {

    private GraphicalObject go;
    private GUI gui;

    public ShapeAction(GraphicalObject go, GUI gui) {
        this.go = go;
        this.gui = gui;

        this.putValue(Action.NAME, go.getShapeName());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.gui.setGUIState(new AddShapeState(gui.getModel(), go));
    }



}
