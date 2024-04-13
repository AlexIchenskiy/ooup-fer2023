package hr.fer.ooup.lv02.zad5.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrintAction implements IAction {

    private final String path;

    public PrintAction(String path) {
        this.path = path;

        try {
            if (!new File(path).exists() && !new File(path).createNewFile()) throw new IOException();
            new FileWriter(path, false).close();
        } catch (Exception e) {
            System.out.println("Could not open file for writing.");
            System.exit(0);
        }
    }

    @Override
    public void perform(List<Integer> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .append(" [LOG] Current data state: [ ");
            for (Integer i : data) sb.append(i).append(" ");
            sb.append("]\n");

            writer.write(sb.toString());
        } catch (Exception e) {
            System.out.println("Could not write to the file.");
        }
    }

}
