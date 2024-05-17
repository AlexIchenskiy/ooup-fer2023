package hr.fer.zemris.ooup.lab3.editor.components;

import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.TextObserver;

import javax.swing.*;
import java.awt.*;

public class TextEditorStatusBar extends JToolBar implements CursorObserver, TextObserver {

    private final TextEditorModel model;

    private final JLabel lineLabel = new JLabel("Line: 1");

    private final JLabel columnLabel = new JLabel("Column: 1");

    private final JLabel linesLabel = new JLabel("Lines: 1");

    public TextEditorStatusBar(TextEditorModel model) {
        this.model = model;
        this.model.addTextListener(this);
        this.model.addCursorListener(this);
        this.linesLabel.setText("Lines: " + this.model.getLines().size());
        this.lineLabel.setText("Line: " + (this.model.getCursorLocation().getLine() + 1));
        this.columnLabel.setText("Column: " + (this.model.getCursorLocation().getColumn() + 1));

        this.initGUI();
    }

    @Override
    public void updateCursorLocation(Location loc) {
        this.lineLabel.setText("Line: " + (loc.getLine() + 1));
        this.columnLabel.setText("Column: " + (loc.getColumn() + 1));
    }

    @Override
    public void updateText() {
        this.linesLabel.setText("Lines: " + this.model.getLines().size());
    }

    private void initGUI() {
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(1, 2));
        dataPanel.add(linesLabel);

        JPanel selectionPanel = new JPanel(new GridLayout(1, 3, 8, 8));
        selectionPanel.add(lineLabel);
        selectionPanel.add(columnLabel);

        dataPanel.add(selectionPanel);

        this.add(dataPanel, BorderLayout.WEST);
    }

}
