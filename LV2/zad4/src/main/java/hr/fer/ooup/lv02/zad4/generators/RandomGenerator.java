package hr.fer.ooup.lv02.zad4.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator  implements IGenerator {

    private final int n, lowerBound, upperBound;
    private final double mean, stddev;
    private final Random random;

    public RandomGenerator(int n, int lowerBound, int upperBound, double mean, double stddev) {
        this.n = n;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.mean = mean;
        this.stddev = stddev;
        this.random = new Random();
    }

    @Override
    public List<Integer> generate() {
        List<Integer> data = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            data.add(
                    Math.min(this.upperBound,
                            Math.max(
                                    this.lowerBound,
                                    (int) Math.abs(1.0 * this.lowerBound +
                                            (this.upperBound - this.lowerBound) *
                                                    (this.stddev * this.random.nextGaussian() + this.mean))))
            );
        }

        return data;
    }

    @Override
    public String toString() {
        return "Random sequence (" + n + " numbers in range [" + lowerBound + ", " + upperBound + "])";
    }

}
