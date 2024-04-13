package hr.fer.ooup.lv02.zad4.testers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterpolationTester implements ITester {

    @Override
    public Integer test(int p, List<Integer> data) {
        List<Integer> sorted = data.stream().sorted().collect(Collectors.toList());
        List<Double> percentilePos = new ArrayList<>();

        int size = sorted.size();

        for (int i = 0; i < size; i++) {
            percentilePos.add(100 * (1.0 * (i + 1) - 0.5) / size);
        }

        if (p < percentilePos.get(0)) return sorted.get(0);
        if (p > percentilePos.get(percentilePos.size() - 1)) return sorted.get(sorted.size() - 1);

        for (int i = 0; i < size - 1; i++) {
            if (p >= percentilePos.get(i) && p <= percentilePos.get(i + 1)) {
                return (int) (sorted.get(i) +
                        size * (p - percentilePos.get(i)) * (sorted.get(i + 1) - sorted.get(i)) / 100);
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "Interpolation percentile tester";
    }
}
