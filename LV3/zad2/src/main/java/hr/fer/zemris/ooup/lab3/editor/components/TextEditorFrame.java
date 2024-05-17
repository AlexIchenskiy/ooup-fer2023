package hr.fer.zemris.ooup.lab3.editor.components;

import hr.fer.zemris.ooup.lab3.editor.TextEditor;
import hr.fer.zemris.ooup.lab3.editor.model.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.model.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.plugins.Plugin;
import hr.fer.zemris.ooup.lab3.editor.singleton.UndoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TextEditorFrame extends JFrame {

    private final String title = "Text editor";

    private final List<String> pluginNames = List.of("VelikoSlovo", "Statistika");

    private final List<AbstractAction> plugins = new ArrayList<>();

    private final String pluginPath = "hr.fer.zemris.ooup.lab3.editor.plugins.";

    public TextEditorFrame(TextEditor editor) throws HeadlessException {
        this.initPlugins(editor);
        this.initGUI(editor);
    }

    private void initPlugins(TextEditor editor) {
        for (String pluginName : this.pluginNames) {
            try {
                @SuppressWarnings("unchecked")
                Class<Plugin> pluginClass = (Class<Plugin>) Class.forName(
                        pluginPath + pluginName
                );

                Constructor<?> constructor = pluginClass.getConstructor();

                Method method = pluginClass.getMethod("execute", TextEditorModel.class,
                        UndoManager.class, ClipboardStack.class);

                Plugin pluginObject = (Plugin) constructor.newInstance();

                AbstractAction action = getPluginAction(editor, pluginObject, method);

                this.plugins.add(action);
            } catch (Exception ignored) {}
        }
    }

    private static AbstractAction getPluginAction(TextEditor editor, Plugin pluginObject, Method method) {
        AbstractAction action = new AbstractAction() {
            private final Plugin plugin = pluginObject;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    method.invoke(plugin, editor.getModel(), editor.getModel().getManager(),
                            editor.getClipboardStack());
                } catch (Exception ignored) {}
            }
        };

        action.putValue("Name", pluginObject.getName());
        return action;
    }

    private void initGUI(TextEditor editor) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        this.setSize(600, 600);
        this.setPreferredSize(new Dimension(600, 600));
        this.setTitle(title);
        this.setLayout(new BorderLayout());

        this.add(editor, BorderLayout.CENTER);
        this.add(new TextEditorToolbar(editor, plugins), BorderLayout.NORTH);
        this.add(new TextEditorStatusBar(editor.getModel()), BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

}
