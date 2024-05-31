package hr.fer.ooup.lv04.paint.gui.action;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.state.EraserState;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EraseAction extends AbstractAction {

    private GUI gui;

    public EraseAction(GUI gui) {
        this.gui = gui;

        this.putValue(Action.NAME, "Brisalo");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.gui.setGUIState(new EraserState(this.gui.getModel()));
    }

}
