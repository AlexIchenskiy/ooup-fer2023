package hr.fer.zemris.ooup.lab3.editor.singleton;

import hr.fer.zemris.ooup.lab3.editor.command.EditAction;
import hr.fer.zemris.ooup.lab3.editor.observer.UndoObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoManager {

    private static UndoManager instance;

    private final Stack<EditAction> undoStack = new Stack<>();

    private final Stack<EditAction> redoStack = new Stack<>();

    private final List<UndoObserver> undoListeners = new ArrayList<>();

    private UndoManager() {}

    public static UndoManager getInstance() {
        if (instance == null) {
            instance = new UndoManager();
        }

        return instance;
    }

    public void undo() {
        if (undoStack.isEmpty()) return;
        EditAction action = undoStack.pop();
        action.executeUndo();
        redoStack.push(action);
        this.notifyUndoListeners();
    }

    public void redo() {
        if (redoStack.isEmpty()) return;
        EditAction action = redoStack.pop();
        action.executeDo();
        undoStack.push(action);
        this.notifyUndoListeners();
    }

    public void push(EditAction c) {
        redoStack.clear();
        undoStack.push(c);
        this.notifyUndoListeners();
    }

    public boolean isUndoEmpty() {
        return this.undoStack.isEmpty();
    }

    public boolean isRedoEmpty() {
        return this.redoStack.isEmpty();
    }

    public void addUndoListener(UndoObserver listener) {
        this.undoListeners.add(listener);
    }

    public void removeUndoListener(UndoObserver listener) {
        this.undoListeners.remove(listener);
    }

    public void notifyUndoListeners() {
        this.undoListeners.forEach(UndoObserver::updateUndo);
    }

}
