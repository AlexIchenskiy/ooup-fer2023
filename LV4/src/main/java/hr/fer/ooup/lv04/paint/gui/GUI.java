package hr.fer.ooup.lv04.paint.gui;

import hr.fer.ooup.lv04.paint.gui.action.*;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;
import hr.fer.ooup.lv04.paint.state.IdleState;
import hr.fer.ooup.lv04.paint.state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    private List<GraphicalObject> objects;
    private List<AbstractAction> actions;

    private DocumentModel model = new DocumentModel();
    private DocumentCanvas canvas;

    private State state = new IdleState();

    private JToolBar toolBar = new JToolBar();

    public GUI(List<GraphicalObject> objects) throws HeadlessException {
        this.objects = objects;

        this.initActions();
        this.initGUI();
        this.initCanvas();
    }

    public DocumentModel getModel() {
        return model;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public State getGUIState() {
        return state;
    }

    public void setGUIState(State state) {
        this.state = state;
    }

    private void initActions() {
        this.actions = new ArrayList<>();

        this.actions.add(new OpenAction(this));
        this.actions.add(new SaveAction(this));
        this.actions.add(new SVGExportAction(this));

        for (GraphicalObject go : this.objects) {
            this.actions.add(new ShapeAction(go, this));
        }

        this.actions.add(new SelectAction(this));
        this.actions.add(new EraseAction(this));
    }

    private void initGUI() {
        this.setTitle("FERPaint");
        this.setSize(720, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setFocusable(true);
        this.requestFocus();

        Container cp = this.getContentPane();
        this.toolBar.setFloatable(false);
        for (AbstractAction action : this.actions) this.toolBar.add(action);

        cp.setLayout(new BorderLayout());

        cp.add(this.toolBar, BorderLayout.PAGE_START);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed");
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setGUIState(new IdleState());
                } else {
                    System.out.println(e.getKeyCode());
                    getGUIState().keyPressed(e.getKeyCode());
                }
            }
        });
    }

    private void initCanvas() {
        this.canvas = new DocumentCanvas(this.model, this);
        this.getContentPane().add(this.canvas, BorderLayout.CENTER);
    }

}
