package hr.fer.zemris.ooup.lab3.editor.model;

import hr.fer.zemris.ooup.lab3.editor.command.*;
import hr.fer.zemris.ooup.lab3.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.SelectionObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.TextObserver;
import hr.fer.zemris.ooup.lab3.editor.singleton.UndoManager;

import java.util.*;

public class TextEditorModel {

    private List<String> lines;

    private LocationRange selectionRange;

    private Location cursorLocation;

    private final List<CursorObserver> cursorListeners = new ArrayList<>();

    private final List<TextObserver> textListeners = new ArrayList<>();

    private final List<SelectionObserver> selectionListeners = new ArrayList<>();

    private final UndoManager manager = UndoManager.getInstance();

    public TextEditorModel(String text) {
        this.lines = new ArrayList<>();
        this.selectionRange = new LocationRange(
                new Location(0, 0),
                new Location(0, 0)
        );
        this.cursorLocation = new Location(0, 0);

        this.setText(text);
    }

    public UndoManager getManager() {
        return manager;
    }

    public void setText(String text) {
        String[] lines = text.split("\n");

        this.lines.addAll(Arrays.asList(lines));
    }

    public String getText() {
        return String.join("\n", this.lines);
    }

    public String getText(LocationRange range) {
        StringBuilder sb = new StringBuilder();

        for (int i = range.getStart().getLine(); i <= range.getEnd().getLine(); i++) {
            String currLine = this.lines.get(i);

            if (i == range.getStart().getLine()) {
                if (range.getStart().getLine() == range.getEnd().getLine()) {
                    sb.append(currLine, range.getStart().getColumn(), range.getEnd().getColumn());
                    break;
                } else {
                    sb.append(currLine.substring(range.getStart().getColumn())).append("\n");
                }

                continue;
            }

            if (i == range.getEnd().getLine()) {
                sb.append(currLine, 0, range.getEnd().getColumn());
                continue;
            }

            sb.append(currLine).append('\n');
        }

        return sb.toString();
    }

    public List<String> getLines() {
        return List.copyOf(this.lines);
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        this.notifyTextListeners();
    }

    public LocationRange getSelectionRange() {
        return new LocationRange(selectionRange);
    }

    public void setSelectionRange(LocationRange selectionRange) {
        this.selectionRange = selectionRange;
        this.notifyTextListeners();
    }

    public Location getCursorLocation() {
        return new Location(this.cursorLocation);
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        this.notifyCursorListeners(cursorLocation);
    }

    public Iterator<String> allLines() {
        return this.lines.iterator();
    }

    public Iterator<String> linesRange(int index1, int index2) {
        return this.lines.subList(index1, index2).iterator();
    }

    public void addCursorListener(CursorObserver listener) {
        this.cursorListeners.add(listener);
    }

    public void addTextListener(TextObserver listener) {
        this.textListeners.add(listener);
    }

    public void addSelectionListener(SelectionObserver listener) {
        this.selectionListeners.add(listener);
    }

    public void removeCursorListener(CursorObserver listener) {
        this.cursorListeners.remove(listener);
    }

    public void removeTextListener(TextObserver listener) {
        this.textListeners.remove(listener);
    }

    public void removeSelectionListener(SelectionObserver listener) {
        this.selectionListeners.remove(listener);
    }

    public void notifyCursorListeners(Location loc) {
        this.cursorListeners.forEach((l) -> l.updateCursorLocation(loc));
    }

    public void notifyTextListeners() {
        this.textListeners.forEach(TextObserver::updateText);
    }

    public void notifySelectionListeners() {
        this.selectionListeners.forEach(SelectionObserver::updateSelection);
    }

    public void moveCursorLeft() {
        int newCol = this.cursorLocation.getColumn() - 1;

        if (newCol < 0 && this.cursorLocation.getLine() > 0) {
            this.cursorLocation.setColumn(this.lines.get(this.cursorLocation.getLine() - 1).length());
            this.moveCursorUp();
            return;
        }

        if (newCol >= 0) {
            this.cursorLocation.setColumn(newCol);
            this.notifyCursorListeners(this.cursorLocation);
        }
    }

    public void moveCursorRight() {
        int newCol = this.cursorLocation.getColumn() + 1;
        int lineLen = this.lines.get(this.cursorLocation.getLine()).length();

        if (newCol > lineLen &&
            this.cursorLocation.getLine() < this.lines.size() - 1) {
            this.cursorLocation.setColumn(0);
            this.moveCursorDown();
            return;
        }

        if (newCol <= lineLen) {
            this.cursorLocation.setColumn(newCol);
            this.notifyCursorListeners(this.cursorLocation);
        }
    }

    public void moveCursorUp() {
        int newLine = this.cursorLocation.getLine() - 1;

        if (newLine < 0) return;

        this.cursorLocation.setLine(newLine);
        if (this.cursorLocation.getColumn() > this.lines.get(newLine).length())
            this.cursorLocation.setColumn(this.lines.get(newLine).length());
        this.notifyCursorListeners(this.cursorLocation);
    }

    public void moveCursorDown() {
        int newLine = this.cursorLocation.getLine() + 1;

        if (newLine > this.lines.size() - 1) return;

        this.cursorLocation.setLine(newLine);
        if (this.cursorLocation.getColumn() > this.lines.get(newLine).length())
            this.cursorLocation.setColumn(this.lines.get(newLine).length());
        this.notifyCursorListeners(this.cursorLocation);
    }

    public void deleteBefore() {
        EditAction action = new DeleteBeforeAction(this);
        action.executeDo();
        manager.push(action);
    }

    public void deleteBeforeExecute() {
        if (this.cursorLocation.getColumn() == 0 && this.cursorLocation.getLine() == 0) return;

        if (this.cursorLocation.getColumn() == 0) {
            String lineToModify = this.lines.get(this.cursorLocation.getLine() - 1);
            this.lines.set(this.cursorLocation.getLine() - 1,
                    lineToModify + this.lines.get(this.cursorLocation.getLine()));
            this.lines.remove(this.cursorLocation.getLine());
            this.cursorLocation.setColumn(lineToModify.length() + 1);
            this.cursorLocation.setLine(this.cursorLocation.getLine() - 1);
            this.notifyTextListeners();
            this.moveCursorLeft();
            return;
        }

        String lineToModify = this.lines.get(this.cursorLocation.getLine());
        this.lines.set(this.cursorLocation.getLine(), lineToModify.substring(0, this.cursorLocation.getColumn() - 1) +
                lineToModify.substring(this.cursorLocation.getColumn()));
        this.notifyTextListeners();
        this.moveCursorLeft();
    }

    public void deleteAfter() {
        EditAction action = new DeleteAfterAction(this);
        action.executeDo();
        manager.push(action);
    }

    public void deleteAfterExecute() {
        if (this.cursorLocation.getColumn() == this.lines.get(this.cursorLocation.getLine()).length()
                && this.cursorLocation.getLine() == this.lines.size() - 1) return;

        if (this.cursorLocation.getColumn() == this.lines.get(this.cursorLocation.getLine()).length()) {
            String lineToModify = this.lines.get(this.cursorLocation.getLine());
            this.lines.set(this.cursorLocation.getLine(),
                    lineToModify + this.lines.get(this.cursorLocation.getLine() + 1));
            this.lines.remove(this.cursorLocation.getLine() + 1);
            this.notifyTextListeners();
            return;
        }

        String lineToModify = this.lines.get(this.cursorLocation.getLine());
        this.lines.set(this.cursorLocation.getLine(), lineToModify.substring(0, this.cursorLocation.getColumn()) +
                lineToModify.substring(this.cursorLocation.getColumn() + 1));
        this.notifyTextListeners();
    }

    public void deleteRange(LocationRange r) {
        EditAction action = new DeleteRangeAction(this, r);
        action.executeDo();
        manager.push(action);
    }

    public void deleteRangeExecute(LocationRange r) {
        List<Integer> linesToRemove = new ArrayList<>();

        for (int i = r.getStart().getLine(); i <= r.getEnd().getLine(); i++) {
            if (i > r.getStart().getLine() && i < r.getEnd().getLine()) {
                linesToRemove.add(i);
                continue;
            }

            if (r.getEnd().getLine() == r.getStart().getLine()) {
                this.lines.set(i, this.lines.get(i).substring(0, r.getStart().getColumn()) +
                        this.lines.get(i).substring(r.getEnd().getColumn()));
                if (this.lines.get(i).isEmpty()) linesToRemove.add(i);;
                break;
            }

            if (i == r.getStart().getLine()) {
                this.lines.set(i, this.lines.get(i).substring(0, r.getStart().getColumn()));
                if (this.lines.get(i).isEmpty()) linesToRemove.add(i);;
                continue;
            }

            if (i == r.getEnd().getLine()) {
                this.lines.set(i, this.lines.get(i).substring(r.getEnd().getColumn()));
                if (this.lines.get(i).isEmpty()) linesToRemove.add(i);
            }
        }

        int firstLine = r.getStart().getLine();
        int lastLine = r.getEnd().getLine();

        linesToRemove = new ArrayList<>(new HashSet<>(linesToRemove));
        linesToRemove.sort(Integer::compare);
        Collections.reverse(linesToRemove);

        for (int i : linesToRemove) this.lines.remove(i);
        linesToRemove.clear();

        if (r.getStart().getColumn() != 0 && firstLine != lastLine && r.getStart().getLine() + 1 < this.lines.size()) {
            this.lines.set(firstLine, this.lines.get(firstLine) + this.lines.get(firstLine + 1));
            int i;
            for (i = firstLine + 1; i < this.lines.size() - 1; i++) {
                this.lines.set(i, this.lines.get(i + 1));
            }
            linesToRemove.add(i);
        }

        linesToRemove = new ArrayList<>(new HashSet<>(linesToRemove));
        linesToRemove.sort(Integer::compare);
        Collections.reverse(linesToRemove);
        for (int i : linesToRemove) this.lines.remove(i);

        this.setCursorLocation(r.getStart());
        this.selectionRange.reset();

        this.notifyTextListeners();
        this.notifyCursorListeners(r.getStart());
    }

    public void checkSelection(boolean selection, Location oldLocation) {
        if (!selection) {
            this.selectionRange.reset();
            this.notifySelectionListeners();
            return;
        }

        if (this.selectionRange.getStart().compare(this.selectionRange.getEnd()) > 0) {
            Location temp = this.selectionRange.getEnd();
            this.selectionRange.setEnd(this.selectionRange.getStart());
            this.selectionRange.setStart(temp);
            this.notifySelectionListeners();
        }

        if (!this.selectionRange.isEmpty() && this.cursorLocation.compare(this.selectionRange.getEnd()) > 0) {
            this.selectionRange.setEnd(cursorLocation);
            this.notifySelectionListeners();
            return;
        }

        if (!this.selectionRange.isEmpty() && this.cursorLocation.compare(this.selectionRange.getStart()) < 0) {
            this.selectionRange.setStart(cursorLocation);
            this.notifySelectionListeners();
            return;
        }

        if (this.selectionRange.isEmpty() && this.cursorLocation.equals(this.selectionRange.getStart())) {
            this.selectionRange.reset();
            this.notifySelectionListeners();
            return;
        }

        if (this.selectionRange.isEmpty()) {
            this.selectionRange = this.cursorLocation.compare(oldLocation) > 0 ?
                    new LocationRange(oldLocation, cursorLocation) : new LocationRange(cursorLocation, oldLocation);
            this.notifySelectionListeners();
        }
    }

    public void insert(char c) {
        EditAction action = new InsertAction(this, c);
        action.executeDo();
        manager.push(action);
    }

    public void insertPerform(char c) {
        if (!this.selectionRange.isEmpty()) this.deleteRange(this.selectionRange);
        this.selectionRange.reset();

        int currLineIndex = this.cursorLocation.getLine();
        String currLine = this.lines.get(currLineIndex);

        if (c == 10) {
            this.lines.set(currLineIndex, currLine.substring(0, this.cursorLocation.getColumn()));
            this.lines.add(currLineIndex + 1, currLine.substring(cursorLocation.getColumn()));
        } else {
            this.lines.set(currLineIndex, currLine.substring(0, this.cursorLocation.getColumn())
                    + c + currLine.substring(this.cursorLocation.getColumn()));
        }
        this.moveCursorRight();
        this.notifyTextListeners();
    }

    public void insert(String text) {
        EditAction action = new InsertAction(this, text);
        action.executeDo();
        manager.push(action);
    }

    public void insertPerform(String text) {
        this.selectionRange.reset();

        for (int i = 0; i < text.length(); i++) {
            this.insert(text.charAt(i));
        }
    }

}
