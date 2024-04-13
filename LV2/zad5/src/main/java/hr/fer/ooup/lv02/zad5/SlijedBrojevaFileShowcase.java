package hr.fer.ooup.lv02.zad5;

import hr.fer.ooup.lv02.zad5.action.AvgAction;
import hr.fer.ooup.lv02.zad5.action.MedianAction;
import hr.fer.ooup.lv02.zad5.action.PrintAction;
import hr.fer.ooup.lv02.zad5.action.SumAction;
import hr.fer.ooup.lv02.zad5.source.DatotecniIzvor;
import hr.fer.ooup.lv02.zad5.source.TipkovnickiIzvor;

public class SlijedBrojevaFileShowcase {

    public static final String IN_FILE_PATH = "data.txt";
    public static final String OUT_FILE_PATH = "logs.txt";

    public static void main(String[] args) {
        System.out.println("Trying to read files from " + IN_FILE_PATH + "...");
        SlijedBrojeva slijedBrojeva = new SlijedBrojeva(new DatotecniIzvor(IN_FILE_PATH));

        slijedBrojeva.addListener(new SumAction());
        slijedBrojeva.addListener(new AvgAction());
        slijedBrojeva.addListener(new MedianAction());
        slijedBrojeva.addListener(new PrintAction(OUT_FILE_PATH));

        System.out.println("Main thread started.");
        slijedBrojeva.kreni();
        System.out.println("Main thread finished. You should see your logs in " + OUT_FILE_PATH + " file.");
    }

}
