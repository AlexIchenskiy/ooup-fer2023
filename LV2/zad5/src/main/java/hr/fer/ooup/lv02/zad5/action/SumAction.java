package hr.fer.ooup.lv02.zad5.action;

import java.util.List;

public class SumAction implements IAction {

    @Override
    public void perform(List<Integer> data) {
        System.out.println("Current sum is: " + data.stream().reduce(0, Integer::sum));
    }

}
