package hr.fer.ooup.lv04.paint.gui.render;

import hr.fer.ooup.lv04.paint.model.Point;

public interface Renderer {

    void drawLine(Point s, Point e);
    void fillPolygon(Point[] points);

}
