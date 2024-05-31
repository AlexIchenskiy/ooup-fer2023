package hr.fer.ooup.lv04.paint.state;

import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;
import hr.fer.ooup.lv04.paint.model.shape.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class EraserState extends IdleState {

    private DocumentModel model;
    private List<Point> selection = new ArrayList<>();
    private List<LineSegment> drawing = new ArrayList<>();

    public EraserState(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        for (Point p : this.selection) {
            GraphicalObject go = this.model.findSelectedGraphicalObject(p);

            if (go != null) {
                this.model.removeGraphicalObject(go);
            }
        }

        for (LineSegment ls : this.drawing) {
            this.model.removeGraphicalObject(ls);
        }

        this.drawing.clear();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (this.selection.contains(mousePoint)) return;

        this.selection.add(mousePoint);

        LineSegment temp;
        if (this.drawing.isEmpty()) {
            temp = new LineSegment(mousePoint, mousePoint);
        } else {
            temp = new LineSegment(mousePoint, this.drawing.get(this.drawing.size() - 1).getHotPoint(0));
        }

        this.drawing.add(temp);
        this.model.addGraphicalObject(temp);
    }

}
