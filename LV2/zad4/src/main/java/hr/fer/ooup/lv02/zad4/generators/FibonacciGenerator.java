package hr.fer.ooup.lv02.zad4.generators;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements IGenerator {

    private final int n;

    public FibonacciGenerator(int n) {
        this.n = n;
    }

    @Override
    public List<Integer> generate() {
        List<Integer> data = new ArrayList<>();

        if (this.n <= 0) return data;

        data.add(1);
        if (this.n == 1) return data;

        data.add(1);
        if (this.n == 2) return data;

        for (int i = 2; i < this.n; i++) {
            data.add(data.get(i - 1) + data.get(i - 2));
        }

        return data;
    }

    @Override
    public String toString() {
        return "Fibonacci sequence (" + n + " numbers)";
    }

}
