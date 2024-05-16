package hr.fer.zemris.ooup.lab3.editor.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class Action extends AbstractAction {

    public Action(String name) {
        this.putValue(Action.NAME, name);
    }

    @Override
    public abstract void actionPerformed(ActionEvent actionEvent);
}
