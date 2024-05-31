package hr.fer.ooup.lv04.paint.gui.render;

import hr.fer.ooup.lv04.paint.model.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SVGRendererImpl implements Renderer {

	private List<String> lines = new ArrayList();
	private String fileName;
	private int width, height;
	
	public SVGRendererImpl(String fileName, int width, int height) {
		// zapamti fileName; u lines dodaj zaglavlje SVG dokumenta:
		// <svg xmlns=... >
		// ...
		this.fileName = fileName;
		this.width = width;
		this.height = height;

		this.lines.add(String.format(
				"<svg version=\"1.1\" width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">",
				width, height));
	}

	public void close() throws IOException {
		// u lines još dodaj završni tag SVG dokumenta: </svg>
		// sve retke u listi lines zapiši na disk u datoteku
		// ...

		this.lines.add("</svg>");
		Files.write(Path.of(this.fileName), lines);
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		// Dodaj u lines redak koji definira linijski segment:
		// <line ... />
		this.lines.add(String.format(
				"<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:blue;stroke-width:2\" />",
				s.getX(), s.getY(), e.getX(), e.getY()));
	}

	@Override
	public void fillPolygon(Point[] points) {
		// Dodaj u lines redak koji definira popunjeni poligon:
		// <polygon points="..." style="stroke: ...; fill: ...;" />
		StringBuilder sb = new StringBuilder();
		sb.append("<polygon points=\"");

		for (int i = 0; i < points.length; i++) {
			sb.append(points[i].getX()).append(",").append(points[i].getY());
			if (i != points.length - 1) sb.append(" ");
		}

		sb.append("\" style=\"stroke:red;fill:blue;\" />");
		this.lines.add(sb.toString());
	}

}