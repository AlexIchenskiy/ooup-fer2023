package hr.fer.ooup.lv04.paint.gui;

import hr.fer.ooup.lv04.paint.gui.render.G2DRendererImpl;
import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;
import hr.fer.ooup.lv04.paint.state.IdleState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DocumentCanvas extends JComponent {

    private DocumentModel model;
    private GUI gui;

    public DocumentCanvas(DocumentModel model, GUI gui) {
        this.model = model;
        this.gui = gui;

        this.model.addDocumentModelListener(this::repaint);

        this.initListeners();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Renderer r = new G2DRendererImpl(g2d);

        for (GraphicalObject go : this.model.list()) {
            go.render(r);
            this.gui.getGUIState().afterDraw(r, go);
        }

        this.gui.getGUIState().afterDraw(r);
    }

    private void initListeners() {
        this.gui.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gui.getGUIState().mouseDown(new Point(e.getX(),
                                e.getY() - gui.getInsets().top - gui.getToolBar().getHeight()),
                        e.isShiftDown(), e.isControlDown());
                gui.requestFocus();
                gui.requestFocusInWindow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gui.getGUIState().mouseUp(new Point(e.getX(),
                        e.getY() - gui.getInsets().top - gui.getToolBar().getHeight()),
                        e.isShiftDown(), e.isControlDown());
            }
        });

        this.gui.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                gui.getGUIState().mouseDragged(new Point(e.getX(),
                        e.getY() - gui.getInsets().top - gui.getToolBar().getHeight()));
            }
        });
    }

}
