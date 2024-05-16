package hr.fer.zemris.ooup.lab3.editor.model;

import java.util.Objects;

public class Location {

    private int line;

    private int column;

    public Location(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Location(Location location) {
        this.line = location.getLine();
        this.column = location.getColumn();
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int compare(Location other) {
        if (this.line == other.line) {
            return Integer.compare(this.column, other.column);
        } else {
            return Integer.compare(this.line, other.line);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return line == location.line && column == location.column;
    }

    @Override
    public String toString() {
        return "Loc (row: " + this.getLine() + ", col: " + this.getColumn() + ")";
    }

}
