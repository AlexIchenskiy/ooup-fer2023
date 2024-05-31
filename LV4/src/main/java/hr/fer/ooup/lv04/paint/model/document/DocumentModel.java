package hr.fer.ooup.lv04.paint.model.document;

import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.observer.DocumentModelListener;
import hr.fer.ooup.lv04.paint.observer.GraphicalObjectListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentModel {

	public final static double SELECTION_PROXIMITY = 10;

	// Kolekcija svih grafičkih objekata:
	private List<GraphicalObject> objects = new ArrayList();

	// Read-Only proxy oko kolekcije grafičkih objekata:
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);

	// Kolekcija prijavljenih promatrača:
	private List<DocumentModelListener> listeners = new ArrayList<>();

	// Kolekcija selektiranih objekata:
	private List<GraphicalObject> selectedObjects = new ArrayList<>();

	// Read-Only proxy oko kolekcije selektiranih objekata:
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

	// Promatrač koji će biti registriran nad svim objektima crteža...
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

		@Override
		public void graphicalObjectChanged(GraphicalObject go) {
			notifyListeners();
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {
			if (go.isSelected() && !selectedObjects.contains(go)) {
				selectedObjects.add(go);
			}

			if (!go.isSelected()) {
				selectedObjects.remove(go);
			}

			notifyListeners();
		}

	};
	
	// Konstruktor...
	public DocumentModel() {}

	// Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
	// i potom obavijeste svi promatrači modela
	public void clear() {
		for (GraphicalObject go : this.objects) go.removeGraphicalObjectListener(this.goListener);

		this.objects.clear();
		this.selectedObjects.clear();

		this.notifyListeners();
	}

	// Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
	public void addGraphicalObject(GraphicalObject obj) {
		obj.addGraphicalObjectListener(this.goListener);

		this.objects.add(obj);
		if (obj.isSelected()) this.selectedObjects.add(obj);

		this.notifyListeners();
	}
	
	// Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
	public void removeGraphicalObject(GraphicalObject obj) {
		obj.removeGraphicalObjectListener(this.goListener);

		if (obj.isSelected()) this.selectedObjects.remove(obj);
		this.objects.remove(obj);

		notifyListeners();
	}

	// Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
	public List<GraphicalObject> list() {
		return this.roObjects;
	}

	// Prijava...
	public void addDocumentModelListener(DocumentModelListener l) {
		this.listeners.add(l);
	}
	
	// Odjava...
	public void removeDocumentModelListener(DocumentModelListener l) {
		this.listeners.remove(l);
	}

	// Obavještavanje...
	public void notifyListeners() {
		for (DocumentModelListener l : this.listeners) l.documentChange();
	}
	
	// Vrati nepromjenjivu listu selektiranih objekata
	public List<GraphicalObject> getSelectedObjects() {
		return this.roSelectedObjects;
	}

	public void deselectAll() {
		while (!selectedObjects.isEmpty()) {
			selectedObjects.get(0).setSelected(false);
		}
	}

	// Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
	// Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
	public void increaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if (index >= this.objects.size() - 1) return;

		Collections.swap(this.objects, index, index + 1);
		notifyListeners();
	}
	
	// Pomakni predani objekt u listi objekata na jedno mjesto ranije...
	public void decreaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if (index < 1) return;

		Collections.swap(this.objects, index, index - 1);
		notifyListeners();
	}
	
	// Pronađi postoji li u modelu neki objekt koji klik na točku koja je
	// predana kao argument selektira i vrati ga ili vrati null. Točka selektira
	// objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
	// SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		for (GraphicalObject go : this.objects) {
			if (go.selectionDistance(mousePoint) < SELECTION_PROXIMITY) {
				return go;
			}
		}

		return null;
	}

	// Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
	// Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
	// udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa 
	// kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije 
	// se pri tome NE dira.
	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		for (int i = 0; i < object.getNumberOfHotPoints(); i++) {
			if (object.getHotPointDistance(i, mousePoint) < SELECTION_PROXIMITY) {
				return i;
			}
		}

		return -1;
	}

}