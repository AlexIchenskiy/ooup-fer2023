package hr.fer.ooup.lv04.paint.gui.render;

import hr.fer.ooup.lv04.paint.model.Point;

import java.awt.*;
import java.util.Arrays;

public class G2DRendererImpl implements Renderer {

	private Graphics2D g2d;
	
	public G2DRendererImpl(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void drawLine(Point s, Point e) {
		// Postavi boju na plavu
		g2d.setColor(Color.BLUE);

		// Nacrtaj linijski segment od S do E
		// (sve to uporabom g2d dobivenog u konstruktoru)
		g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
	}

	@Override
	public void fillPolygon(Point[] points) {
		int[] xPoints = Arrays.stream(points).mapToInt(Point::getX).toArray();
		int[] yPoints = Arrays.stream(points).mapToInt(Point::getY).toArray();

		// Postavi boju na plavu
		g2d.setColor(Color.BLUE);
		// Popuni poligon definiran danim točkama
		g2d.fillPolygon(xPoints, yPoints, points.length);
		// Postavi boju na crvenu
		g2d.setColor(Color.RED);
		// Nacrtaj rub poligona definiranog danim točkama
		// (sve to uporabom g2d dobivenog u konstruktoru)
		g2d.drawPolygon(xPoints, yPoints, points.length);
	}

}