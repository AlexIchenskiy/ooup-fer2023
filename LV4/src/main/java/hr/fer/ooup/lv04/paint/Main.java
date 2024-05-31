package hr.fer.ooup.lv04.paint;

import hr.fer.ooup.lv04.paint.gui.GUI;
import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.shape.LineSegment;
import hr.fer.ooup.lv04.paint.model.shape.Oval;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<GraphicalObject> objects = new ArrayList<>();

        objects.add(new LineSegment());
        objects.add(new Oval());

        GUI gui = new GUI(objects);
        gui.setVisible(true);
    }

}
