package hr.fer.ooup.lv04.paint.observer;

import hr.fer.ooup.lv04.paint.model.GraphicalObject;

public interface GraphicalObjectListener {

    // Poziva se kad se nad objektom promjeni bio što...
    void graphicalObjectChanged(GraphicalObject go);

    // Poziva se isključivo ako je nad objektom promjenjen status selektiranosti
    // (baš objekta, ne njegovih hot-point-a).
    void graphicalObjectSelectionChanged(GraphicalObject go);

}
