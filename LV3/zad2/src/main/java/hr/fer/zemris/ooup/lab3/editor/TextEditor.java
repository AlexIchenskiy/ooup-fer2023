package hr.fer.zemris.ooup.lab3.editor;

import hr.fer.zemris.ooup.lab3.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.model.Location;
import hr.fer.zemris.ooup.lab3.editor.model.LocationRange;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.TextObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class TextEditor extends JPanel implements CursorObserver, TextObserver {

    private final TextEditorModel model;

    private final ClipboardStack clipboardStack = new ClipboardStack();

    public TextEditor(TextEditorModel model) throws HeadlessException {
        this.model = model;
        this.initGUI();
    }

    public TextEditorModel getModel() {
        return model;
    }

    public ClipboardStack getClipboardStack() {
        return clipboardStack;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        LocationRange selectionRange = this.model.getSelectionRange();
        if (!selectionRange.isEmpty()) {
            g.setColor(Color.PINK);
            for (int i = selectionRange.getStart().getLine(); i <= selectionRange.getEnd().getLine(); i++) {
                if (i == selectionRange.getStart().getLine()) {
                    g.fillRect(selectionRange.getStart().getColumn() * 10 + 10, 20 * (i + 1) - 15,
                            ((selectionRange.getStart().getLine() == selectionRange.getEnd().getLine() ?
                                    selectionRange.getEnd().getColumn() :
                                    model.getLines().get(i).length()) - selectionRange.getStart().getColumn()) * 10,
                            20);
                    continue;
                }

                if (i == selectionRange.getEnd().getLine()) {
                    g.fillRect(10, 20 * (i + 1) - 15, selectionRange.getEnd().getColumn() * 10, 20);
                    continue;
                }

                g.fillRect(10, 20 * (i + 1) - 15, this.model.getLines().get(i).length() * 10, 20);
            }
        }

        g.setColor(Color.BLACK);
        Iterator<String> it = this.model.allLines();
        int count = 0;
        while (it.hasNext()) {
            g.setFont(new Font("Monospaced", Font.PLAIN, 16));
            g.drawString(it.next(), 10, 20 * (count + 1));
            count++;
        }

        Location cursorLocation = this.model.getCursorLocation();
        g.drawLine(10 + cursorLocation.getColumn() * 10, 20 * (cursorLocation.getLine() + 1) - 10,
                10 + cursorLocation.getColumn() * 10, 20 * (cursorLocation.getLine() + 1));
    }

    @Override
    public void updateCursorLocation(Location loc) {
        this.repaint();
    }

    @Override
    public void updateText() {
        this.repaint();
    }

    private void initGUI() {
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Location loc = model.getCursorLocation();

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.moveCursorLeft();
                    model.checkSelection(e.isShiftDown(), loc);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.moveCursorRight();
                    model.checkSelection(e.isShiftDown(), loc);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    model.moveCursorUp();
                    model.checkSelection(e.isShiftDown(), loc);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    model.moveCursorDown();
                    model.checkSelection(e.isShiftDown(), loc);
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (model.getSelectionRange().isEmpty()) {
                        model.deleteBefore();
                    } else {
                        model.deleteRange(model.getSelectionRange());
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (model.getSelectionRange().isEmpty()) {
                        model.deleteAfter();
                    } else {
                        model.deleteRange(model.getSelectionRange());
                    }
                } else if (e.isShiftDown() && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    model.insert(clipboardStack.pop());
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    String text = model.getText(model.getSelectionRange());
                    if (!text.isEmpty()) clipboardStack.push(text);
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                    String text = model.getText(model.getSelectionRange());
                    if (!text.isEmpty()) {
                        clipboardStack.push(text);
                        model.deleteRange(model.getSelectionRange());
                    }
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    model.insert(clipboardStack.peek());
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                    model.getManager().undo();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
                    model.getManager().redo();
                } else if ((Character.isDefined(e.getKeyChar()) && !Character.isISOControl(e.getKeyChar())) ||
                        (e.getKeyCode() == KeyEvent.VK_ENTER)) {
                    model.insert(e.getKeyChar());
                }
            }
        });

        this.model.addCursorListener(this);
        this.model.addTextListener(this);
    }

}
