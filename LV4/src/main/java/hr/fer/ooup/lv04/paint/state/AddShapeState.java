package hr.fer.ooup.lv04.paint.state;

import hr.fer.ooup.lv04.paint.model.GraphicalObject;
import hr.fer.ooup.lv04.paint.model.Point;
import hr.fer.ooup.lv04.paint.model.document.DocumentModel;

public class AddShapeState extends IdleState {
	
	private GraphicalObject prototype;
	private DocumentModel model;
	
	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		this.model = model;
		this.prototype = prototype;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		// dupliciraj zapamćeni prototip, pomakni ga na poziciju miša i dodaj u model
		GraphicalObject go = this.prototype.duplicate();

		go.translate(mousePoint);

		this.model.addGraphicalObject(go);
	}

}