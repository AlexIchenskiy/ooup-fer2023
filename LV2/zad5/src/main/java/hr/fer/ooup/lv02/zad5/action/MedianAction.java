package hr.fer.ooup.lv02.zad5.action;

import java.util.List;
import java.util.stream.Collectors;

public class MedianAction implements IAction {

    @Override
    public void perform(List<Integer> data) {
        List<Integer> sorted = data.stream().sorted().collect(Collectors.toList());

        System.out.print("Current median is: ");

        if (sorted.size() % 2 != 0) {
            System.out.println(sorted.get(sorted.size() / 2));
            return;
        }

        System.out.println(1.0 * (sorted.get((sorted.size() - 1) / 2) + sorted.get(sorted.size() / 2)) / 2.0);
    }

}
