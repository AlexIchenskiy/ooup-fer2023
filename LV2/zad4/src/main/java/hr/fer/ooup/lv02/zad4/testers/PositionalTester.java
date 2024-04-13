package hr.fer.ooup.lv02.zad4.testers;

import java.util.List;
import java.util.stream.Collectors;

public class PositionalTester implements ITester {

    @Override
    public Integer test(int p, List<Integer> data) {
        List<Integer> sorted = data.stream().sorted().collect(Collectors.toList());

        double n_p = (double) (p * sorted.size()) / 100 + 0.5;

        return sorted.get((int) Math.ceil(n_p) - 1);
    }

    @Override
    public String toString() {
        return "Positional percentile tester";
    }
}
