package hr.fer.ooup.lv02.zad5.action;

import java.util.List;

public class AvgAction implements IAction {

    @Override
    public void perform(List<Integer> data) {
        System.out.println("Current average is: " +
                1.0 * data.stream().reduce(0, Integer::sum) / data.size());
    }

}
