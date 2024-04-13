package hr.fer.ooup.lv02.zad5;

import hr.fer.ooup.lv02.zad5.action.IAction;
import hr.fer.ooup.lv02.zad5.source.ISource;

import java.util.ArrayList;
import java.util.List;

public class SlijedBrojeva {

    private final List<Integer> collection;
    private final ISource source;

    private List<IAction> listeners;

    public SlijedBrojeva(ISource source) {
        this.source = source;
        this.collection = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public SlijedBrojeva(ISource source, List<IAction> listeners) {
        this(source);
        this.listeners = listeners;
    }

    public void addListener(IAction listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IAction listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners() {
        for (IAction listener : this.listeners) listener.perform(this.collection);
    }

    public void kreni() {
        while (true) {
            long temp = System.currentTimeMillis();

            int res = this.source.getNumber();
            if (res == -1) break;

            this.collection.add(res);

            this.notifyListeners();

            try {
                Thread.sleep(Math.min(System.currentTimeMillis() - temp, 1000));
            } catch (InterruptedException ignored) {
            }
        }
    }

}
