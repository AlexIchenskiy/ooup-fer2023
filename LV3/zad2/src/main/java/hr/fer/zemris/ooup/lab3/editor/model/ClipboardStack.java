package hr.fer.zemris.ooup.lab3.editor.model;

import hr.fer.zemris.ooup.lab3.editor.observer.ClipboardObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {

    private final Stack<String> texts = new Stack<>();

    private final List<ClipboardObserver> listeners = new ArrayList<>();

    public String pop() {
        String returnText = this.texts.isEmpty() ? "" : this.texts.pop();
        this.notifyClipboardListeners();

        return returnText;
    }

    public String push(String text) {
        String returnText = this.texts.push(text);
        this.notifyClipboardListeners();

        return returnText;
    }

    public String peek() {
        String returnText = this.texts.isEmpty() ? "" : this.texts.peek();

        return returnText;
    }

    public boolean isEmpty() {
        return this.texts.isEmpty();
    }

    public void clear() {
        this.texts.clear();
    }

    public void addClipboardListener(ClipboardObserver listener) {
        this.listeners.add(listener);
    }

    public void removeClipboardListener(ClipboardObserver listener) {
        this.listeners.remove(listener);
    }

    public void notifyClipboardListeners() {
        this.listeners.forEach(ClipboardObserver::updateClipboard);
    }

}
