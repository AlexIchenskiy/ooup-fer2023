package hr.fer.ooup.lv04.paint.util;

import hr.fer.ooup.lv04.paint.model.Point;

public class GeometryUtil {

    public static double distanceFromPoint(Point point1, Point point2) {
        // izračunaj euklidsku udaljenost između dvije točke ...
        double dx = point2.getX() - point1.getX();
        double dy = point2.getY() - point1.getY();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        // Izračunaj koliko je točka P udaljena od linijskog segmenta određenog
        // početnom točkom S i završnom točkom E. Uočite: ako je točka P iznad/ispod
        // tog segmenta, ova udaljenost je udaljenost okomice spuštene iz P na S-E.
        // Ako je točka P "prije" točke S ili "iza" točke E, udaljenost odgovara
        // udaljenosti od P do početne/konačne točke segmenta.
        double sx = e.getX() - s.getX();
        double sy = e.getY() - s.getY();
        double d = sx * sx + sy * sy;

        if (d == 0.0) {
            return distanceFromPoint(s, p);
        }

        double t = ((p.getX() - s.getX()) * sx + (p.getY() - s.getY()) * sy) / d;

        if (t < 0.0) {
            return distanceFromPoint(s, p);
        } else if (t > 1.0) {
            return distanceFromPoint(e, p);
        }

        return distanceFromPoint(new Point((int) Math.round(s.getX() + t * sx),
                (int) Math.round(s.getY() + t * sy)), p);
    }

}
