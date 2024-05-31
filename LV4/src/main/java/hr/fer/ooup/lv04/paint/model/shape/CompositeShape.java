package hr.fer.ooup.lv04.paint.model.shape;

import hr.fer.ooup.lv04.paint.gui.render.Renderer;
import hr.fer.ooup.lv04.paint.model.AbstractGraphicalObject;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class CompositeShape extends AbstractGraphicalObject {

    private List<GraphicalObject> objects;

    public CompositeShape(List<GraphicalObject> objects) {
        super(null);
        this.objects = objects;
    }

    public List<GraphicalObject> getObjects() {
        return objects;
    }

    @Override
    public Rectangle getBoundingBox() {
        int minX = (int) this.objects.stream().map(GraphicalObject::getBoundingBox).mapToDouble(Rectangle::getX)
                .min().orElseThrow();

        int minY = (int) this.objects.stream().map(GraphicalObject::getBoundingBox).mapToDouble(Rectangle::getY)
                .min().orElseThrow();

        int maxX = (int) this.objects.stream().map(GraphicalObject::getBoundingBox).mapToDouble((r) ->
                        r.getX() + r.getWidth())
                .max().orElseThrow();

        int maxY = (int) this.objects.stream().map(GraphicalObject::getBoundingBox).mapToDouble((r) ->
                        r.getY() + Math.abs(r.getHeight()))
                .max().orElseThrow();

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return objects.stream().min(Comparator.comparingDouble(o -> o.selectionDistance(mousePoint))).orElseThrow()
                .selectionDistance(mousePoint);
    }

    @Override
    public void render(Renderer r) {
        for (GraphicalObject go : this.objects) go.render(r);
    }

    @Override
    public String getShapeName() {
        return "Composite";
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public GraphicalObject duplicate() {
        return new CompositeShape(List.of(
                this.objects.stream().map(GraphicalObject::duplicate).toArray(GraphicalObject[]::new)));
    }

    @Override
    public void save(List<String> rows) {
        for (GraphicalObject go : this.objects) go.save(rows);
        rows.add(String.format("%s %d", this.getShapeID(), this.objects.size()));
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int toFetch = Integer.parseInt(data.trim());
        List<GraphicalObject> gos = new ArrayList<>();

        for (int i = 0; i < toFetch; i++) gos.add(stack.pop());

        stack.add(new CompositeShape(gos));
    }

}
