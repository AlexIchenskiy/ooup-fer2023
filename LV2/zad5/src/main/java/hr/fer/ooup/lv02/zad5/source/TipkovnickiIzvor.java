package hr.fer.ooup.lv02.zad5.source;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TipkovnickiIzvor implements ISource {

    @Override
    public int getNumber() {
        try {
            int res = new Scanner(System.in).nextInt();
            if (res < 0) throw new InputMismatchException();
            return res;
        } catch (InputMismatchException e) {
            return -1;
        }
    }
}
