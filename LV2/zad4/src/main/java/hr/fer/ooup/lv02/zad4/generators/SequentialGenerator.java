package hr.fer.ooup.lv02.zad4.generators;

import java.util.ArrayList;
import java.util.List;

public class SequentialGenerator implements IGenerator {

    private final int lowerBound, upperBound, step;

    public SequentialGenerator(int lowerBound, int upperBound, int step) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> data = new ArrayList<>();

        for (int i = this.lowerBound; i <= this.upperBound; i += this.step) data.add(i);

        return data;
    }

    @Override
    public String toString() {
        return "Range sequence (numbers in range [" + this.lowerBound + ", " + this.upperBound
                + "] with step " + this.step + ")";
    }

}
