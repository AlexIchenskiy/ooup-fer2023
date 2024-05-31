package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.state.SelectShapeState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectAction extends AbstractAction {

    private GUI gui;

    public SelectAction(GUI gui) {
        this.gui = gui;

        this.putValue(Action.NAME, "Selektiraj");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.gui.setGUIState(new SelectShapeState(gui.getModel()));
    }

}
