package hr.fer.ooup.lv02.zad5;

import hr.fer.ooup.lv02.zad5.action.AvgAction;
import hr.fer.ooup.lv02.zad5.action.MedianAction;
import hr.fer.ooup.lv02.zad5.action.PrintAction;
import hr.fer.ooup.lv02.zad5.action.SumAction;
import hr.fer.ooup.lv02.zad5.source.DatotecniIzvor;
import hr.fer.ooup.lv02.zad5.source.ISource;
import hr.fer.ooup.lv02.zad5.source.TipkovnickiIzvor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SlijedBrojevaDemo {

    public static void main(String[] args) {
        try {
            SlijedBrojeva slijedBrojeva;
            ISource source;

            System.out.println("Please select data source.\n1. Keyboard\n2. File");
            int select = new Scanner(System.in).nextInt();

            switch (select) {
                case 1:
                    source = new TipkovnickiIzvor();
                    break;
                case 2:
                    System.out.print("Please specify file path: ");
                    String path = new Scanner(System.in).nextLine();
                    source = new DatotecniIzvor(path);
                    break;
                default:
                    System.out.println("Unrecognized source.");
                    return;
            }

            slijedBrojeva = new SlijedBrojeva(source);

            System.out.println("Do you want to add sum listener? 1 - yes, 0 - no");
            select = new Scanner(System.in).nextInt();
            if (select == 1) slijedBrojeva.addListener(new SumAction());

            System.out.println("Do you want to add avg listener? 1 - yes, 0 - no");
            select = new Scanner(System.in).nextInt();
            if (select == 1) slijedBrojeva.addListener(new AvgAction());

            System.out.println("Do you want to add median listener? 1 - yes, 0 - no");
            select = new Scanner(System.in).nextInt();
            if (select == 1) slijedBrojeva.addListener(new MedianAction());

            System.out.println("Do you want to add print listener? 1 - yes, 0 - no");
            select = new Scanner(System.in).nextInt();
            if (select == 1) {
                System.out.print("Please specify file path: ");
                String path = new Scanner(System.in).nextLine();
                slijedBrojeva.addListener(new PrintAction(path));
            }

            slijedBrojeva.kreni();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
        }
    }

}
