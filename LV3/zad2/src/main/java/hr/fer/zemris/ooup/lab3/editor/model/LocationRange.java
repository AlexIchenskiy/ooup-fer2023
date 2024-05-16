package hr.fer.zemris.ooup.lab3.editor.model;

public class LocationRange {

    private Location start;

    private Location end;

    public LocationRange(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public LocationRange(LocationRange r) {
        this.start = new Location(r.getStart());
        this.end = new Location(r.getEnd());
    }

    public Location getStart() {
        return new Location(this.start.getLine(), this.start.getColumn());
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return new Location(this.end.getLine(), this.end.getColumn());
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public void reset() {
        this.start = new Location(-1, -1);
        this.end = new Location(-1, -1);
    }

    public boolean isEmpty() {
        return this.start.equals(this.end);
    }

    @Override
    public String toString() {
        return "Range (" + this.getStart() + " - " + this.getEnd() + ")";
    }
}
