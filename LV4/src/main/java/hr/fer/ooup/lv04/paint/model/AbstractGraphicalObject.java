package hr.fer.ooup.lv04.paint.model;

import hr.fer.ooup.lv04.paint.observer.GraphicalObjectListener;
import hr.fer.ooup.lv04.paint.util.GeometryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGraphicalObject implements GraphicalObject {

    protected Point[] hotPoints;
    protected boolean[] hotPointSelected;
    protected boolean selected;

    protected List<GraphicalObjectListener> listeners = new ArrayList<>();

    public AbstractGraphicalObject(Point[] hotPoints) {
        if (hotPoints == null) {
            this.hotPoints = new Point[0];
            this.hotPointSelected = new boolean[0];
            return;
        }

        this.hotPoints = hotPoints;
        this.hotPointSelected = new boolean[hotPoints.length];
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;

        this.notifySelectionListeners();
    }

    @Override
    public int getNumberOfHotPoints() {
        return this.hotPoints.length;
    }

    @Override
    public Point getHotPoint(int index) {
        return this.hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point point) {
        if (index > this.hotPoints.length) return;

        this.hotPoints[index] = point;

        this.notifyListeners();
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return this.hotPointSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        this.hotPointSelected[index] = selected;

        this.notifySelectionListeners();
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return GeometryUtil.distanceFromPoint(this.hotPoints[index], mousePoint);
    }

    @Override
    public void translate(Point delta) {
        for (int i = 0; i < this.hotPoints.length; i++) {
            this.hotPoints[i] = this.hotPoints[i].translate(delta);
        }

        this.notifyListeners();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        this.listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        this.listeners.remove(l);
    }

    public void notifyListeners() {
        for (GraphicalObjectListener l : this.listeners) {
            l.graphicalObjectChanged(this);
        }
    }

    public void notifySelectionListeners() {
        for (GraphicalObjectListener l : this.listeners) {
            l.graphicalObjectSelectionChanged(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getShapeName()).append(": \n");
        for (Point p : this.hotPoints) sb.append(p).append("\n");
        sb.append("\n");

        return sb.toString();
    }
}
