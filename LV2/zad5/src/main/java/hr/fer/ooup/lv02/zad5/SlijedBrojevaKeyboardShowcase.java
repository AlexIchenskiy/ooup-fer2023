package hr.fer.ooup.lv02.zad5;

import hr.fer.ooup.lv02.zad5.action.AvgAction;
import hr.fer.ooup.lv02.zad5.action.MedianAction;
import hr.fer.ooup.lv02.zad5.action.PrintAction;
import hr.fer.ooup.lv02.zad5.action.SumAction;
import hr.fer.ooup.lv02.zad5.source.TipkovnickiIzvor;

public class SlijedBrojevaKeyboardShowcase {

    public static final String OUT_FILE_PATH = "logs.txt";

    public static void main(String[] args) {
        SlijedBrojeva slijedBrojeva = new SlijedBrojeva(new TipkovnickiIzvor());

        slijedBrojeva.addListener(new SumAction());
        slijedBrojeva.addListener(new AvgAction());
        slijedBrojeva.addListener(new MedianAction());
        slijedBrojeva.addListener(new PrintAction("logs.txt"));

        System.out.println("Main thread started. Writing logs to " + OUT_FILE_PATH + " file. " +
                "Enter integer numbers one by one.");
        slijedBrojeva.kreni();
    }

}
