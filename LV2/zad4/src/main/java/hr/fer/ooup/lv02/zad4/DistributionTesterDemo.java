package hr.fer.ooup.lv02.zad4;

import hr.fer.ooup.lv02.zad4.generators.FibonacciGenerator;
import hr.fer.ooup.lv02.zad4.generators.IGenerator;
import hr.fer.ooup.lv02.zad4.generators.RandomGenerator;
import hr.fer.ooup.lv02.zad4.generators.SequentialGenerator;
import hr.fer.ooup.lv02.zad4.testers.ITester;
import hr.fer.ooup.lv02.zad4.testers.InterpolationTester;
import hr.fer.ooup.lv02.zad4.testers.PositionalTester;

import java.util.List;

public class DistributionTesterDemo {

    public static void main(String[] args) {
        List<IGenerator> generators = List.of(
                new SequentialGenerator(10, 20, 2),
                new RandomGenerator(10, 5, 10, 0, 0.6),
                new FibonacciGenerator(5)
        );

        List<ITester> testers = List.of(
                new InterpolationTester(),
                new PositionalTester()
        );

        DistributionTester distributionTester;

        for (IGenerator generator : generators) {
            for (ITester tester : testers) {
                distributionTester = new DistributionTester(generator, tester);
                distributionTester.testDistributions();
            }
        }
    }

}
