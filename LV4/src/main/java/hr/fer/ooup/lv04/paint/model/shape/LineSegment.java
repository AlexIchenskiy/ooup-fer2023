package hr.fer.ooup.lv04.paint.model.shape;

import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.AbstractGraphicalObject;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.Rectangle;
import hr.fer.ooup.lv04.paint.util.GeometryUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LineSegment extends AbstractGraphicalObject {

    public LineSegment() {
        super(new Point[]{new Point(0, 0), new Point(0, 10)});
    }

    public LineSegment(Point start, Point end) {
        super(new Point[]{start, end});
    }

    public LineSegment(LineSegment ls) {
        super(Arrays.stream(ls.hotPoints).map(Point::new).toArray(Point[]::new));
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(
                Math.min(this.hotPoints[0].getX(), this.hotPoints[1].getX()),
                Math.min(this.hotPoints[0].getY(), this.hotPoints[1].getY()),
                Math.abs(this.hotPoints[1].getX() - this.hotPoints[0].getX()),
                Math.abs(this.hotPoints[1].getY() - this.hotPoints[0].getY())
        );
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(this.getHotPoint(0), this.getHotPoint(1), mousePoint);
    }

    @Override
    public String getShapeName() {
        return "Linija";
    }

    @Override
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(this);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(this.hotPoints[0], this.hotPoints[1]);
    }

    @Override
    public void save(List<String> rows) {
        rows.add(String.format("%s %d %d %d %d",
                this.getShapeID(), this.hotPoints[0].getX(), this.hotPoints[0].getY(),
                this.hotPoints[1].getX(), this.hotPoints[1].getY()));
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int[] pointData = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
        stack.add(new LineSegment(new Point(pointData[0], pointData[1]), new Point(pointData[2], pointData[3])));
    }
}
