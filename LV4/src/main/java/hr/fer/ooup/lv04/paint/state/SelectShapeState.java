package hr.fer.ooup.lv04.paint.state;

import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.Rectangle;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;
import hr.fer.ooup.lv04.paint.model.shape.CompositeShape;

import java.util.ArrayList;
import java.util.List;

public class SelectShapeState extends IdleState {

    private DocumentModel model;

    private GraphicalObject selected = null;
    int selectedPointIndex = -1;

    public SelectShapeState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject selected = this.model.findSelectedGraphicalObject(mousePoint);

        boolean wasSelected = false;
        if (selected != null) {
            wasSelected = selected.isSelected();
        }

        if (!ctrlDown) this.model.deselectAll();

        if (selected != null) {
            this.selected = selected;
            int selectedPointIndex = this.model.findSelectedHotPoint(selected, mousePoint);

            if (selectedPointIndex != -1) {
                this.selectedPointIndex = selectedPointIndex;
                selected.setSelected(true);
                return;
            }

            selected.setSelected(!wasSelected);
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (this.selected != null && this.model.getSelectedObjects().size() == 1) {
            if (this.selectedPointIndex != -1) {
                this.selected.setHotPoint(this.selectedPointIndex, mousePoint);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == 71) { // G
            List<GraphicalObject> objects = new ArrayList<>(this.model.getSelectedObjects());
            CompositeShape composite = new CompositeShape(objects);

            for (GraphicalObject go : objects) model.removeGraphicalObject(go);

            model.addGraphicalObject(composite);
            composite.setSelected(true);
        } else if (keyCode == 85) { // U
            List<GraphicalObject> objects = new ArrayList<>(this.model.getSelectedObjects());

            if (objects.size() == 1 && objects.get(0) instanceof CompositeShape composite) {
                for (GraphicalObject go : composite.getObjects()) model.addGraphicalObject(go);

                composite.setSelected(false);
                model.removeGraphicalObject(composite);
            }
        } else if (keyCode == 45) {
            if (this.selected != null) model.decreaseZ(this.selected);
        } else if (keyCode == 61) {
            if (this.selected != null) model.increaseZ(this.selected);
        } else if (keyCode == 37) {
            if (this.selected != null) selected.translate(new Point(-1, 0));
        } else if (keyCode == 38) {
            if (this.selected != null) selected.translate(new Point(0, -1));
        } else if (keyCode == 39) {
            if (this.selected != null) selected.translate(new Point(1, 0));
        } else if (keyCode == 40) {
            if (this.selected != null) selected.translate(new Point(0, 1));
        }
    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject go) {
        if (go.isSelected()) {
            paintBoundingBox(r, go.getBoundingBox());
        }

        if (go.isSelected() && this.model.getSelectedObjects().size() == 1) {
            for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
                paintHotPoint(r, go.getHotPoint(i));
            }
        }
    }

    private void paintBoundingBox(Renderer r, Rectangle bb) {
        r.drawLine(new Point((int)bb.getX(), (int)bb.getY()),
                new Point((int)bb.getX() + (int)bb.getWidth(), (int)bb.getY()));

        r.drawLine(new Point((int)bb.getX(), (int)bb.getY()),
                new Point((int)bb.getX(), (int)bb.getY() + (int)bb.getHeight()));

        r.drawLine(new Point((int)bb.getX() + (int)bb.getWidth(), (int)bb.getY()),
                new Point((int)bb.getX() + (int)bb.getWidth(), (int)bb.getY() + (int)bb.getHeight()));

        r.drawLine(new Point((int)bb.getX(), (int)bb.getY() + (int)bb.getHeight()),
                new Point((int)bb.getX() + (int)bb.getWidth(), (int)bb.getY() + (int)bb.getHeight()));
    }

    private void paintHotPoint(Renderer r, Point p) {
        int size = 6;
        Rectangle bb = new Rectangle(
                p.getX() - size / 2,
                p.getY() - size / 2,
                size, size
        );

        paintBoundingBox(r, bb);
    }

}
