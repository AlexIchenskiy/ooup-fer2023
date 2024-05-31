package hr.fer.ooup.lv04.paint.model.shape;

import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.AbstractGraphicalObject;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.Rectangle;
import hr.fer.ooup.lv04.paint.util.GeometryUtil;

import java.util.*;

public class Oval extends AbstractGraphicalObject {

    public Oval() {
        super(new Point[]{new Point(10, 0), new Point(0, 10)});
    }

    public Oval(Point right, Point bottom) {
        super(new Point[]{right, bottom});
    }

    public Oval(Oval oval) {
        super(Arrays.stream(oval.hotPoints).map(Point::new).toArray(Point[]::new));
    }

    @Override
    public Rectangle getBoundingBox() {
        Point right = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getX)).orElseThrow();
        Point bottom = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getY)).orElseThrow();

        return new Rectangle(
                right.getX() - 2 * Math.abs(right.getX() - bottom.getX()),
                bottom.getY() - 2 * Math.abs(right.getY() - bottom.getY()),
                2 * Math.abs(right.getX() - bottom.getX()),
                2 * Math.abs(right.getY() - bottom.getY())
        );
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        Point right = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getX)).orElseThrow();
        Point bottom = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getY)).orElseThrow();

        Point center = new Point(bottom.getX(), right.getY());

        double a = right.getX() - bottom.getX();
        double b = right.getY() - bottom.getY();

        double theta = 0.0;
        double step = 0.01;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < 360 / step; i++) {
            Point temp = new Point((int) Math.round(center.getX() + a * Math.cos(theta)),
                    (int) Math.round(center.getY() + b * Math.sin(theta)));

            double distance = GeometryUtil.distanceFromPoint(mousePoint, temp);

            if (distance < minDistance) {
                minDistance = distance;
            }

            theta += step;
        }

        return minDistance;
    }

    @Override
    public String getShapeName() {
        return "Oval";
    }

    @Override
    public String getShapeID() {
        return "@OVAL";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(this);
    }

    @Override
    public void render(Renderer r) {
        Point right = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getX)).orElseThrow();
        Point bottom = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getY)).orElseThrow();

        Point center = new Point(bottom.getX(), right.getY());

        double a = right.getX() - bottom.getX();
        double b = right.getY() - bottom.getY();

        List<Point> points = new ArrayList<>();

        for (int i = 0; i < 360; i++) {
            double theta = (2 * Math.PI) / 360 * i;
            points.add(new Point((int) Math.round(center.getX() + a * Math.cos(theta)),
                    (int) Math.round(center.getY() + b * Math.sin(theta))));
        }

        r.fillPolygon(points.toArray(new Point[0]));
    }

    @Override
    public void save(List<String> rows) {
        Point right = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getX)).orElseThrow();
        Point bottom = Arrays.stream(this.hotPoints).max(Comparator.comparingDouble(Point::getY)).orElseThrow();

        rows.add(String.format("%s %d %d %d %d",
                this.getShapeID(), right.getX(), right.getY(), bottom.getX(), bottom.getY()));
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int[] pointData = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
        stack.add(new Oval(new Point(pointData[0], pointData[1]), new Point(pointData[2], pointData[3])));
    }
}
