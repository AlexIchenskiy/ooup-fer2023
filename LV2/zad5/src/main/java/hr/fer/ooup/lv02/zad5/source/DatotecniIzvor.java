package hr.fer.ooup.lv02.zad5.source;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DatotecniIzvor implements ISource {

    private Scanner scanner;

    public DatotecniIzvor(String path) {
        try {
            if (!new File(path).exists() && !new File(path).createNewFile()) throw new IOException();
            this.scanner = new Scanner(new File(path));
        } catch (Exception e) {
            System.out.println("Could not open file for reading.");
            System.exit(0);
        }
    }

    @Override
    public int getNumber() {
        return scanner.hasNextInt() ? scanner.nextInt() : -1;
    }

}
