package hr.fer.ooup.lv02.zad4;

import hr.fer.ooup.lv02.zad4.generators.IGenerator;
import hr.fer.ooup.lv02.zad4.testers.ITester;

import java.util.List;

public class DistributionTester {

    private IGenerator generator;
    private ITester tester;

    public DistributionTester(IGenerator generator, ITester tester) {
        this.generator = generator;
        this.tester = tester;
    }

    public void setGenerator(IGenerator generator) {
        this.generator = generator;
    }

    public void setTester(ITester tester) {
        this.tester = tester;
    }

    public void testDistributions() {
        List<Integer> data = this.generator.generate();

        System.out.print("Testing the " + this.generator.toString() + ": \n[ ");
        for (Integer i : data) System.out.print(i + " ");
        System.out.println("] \nusing the " + this.tester.toString() + ": ");

        for (int i = 10; i <= 90; i += 10) {
            System.out.println(i + "th percentile is " + this.tester.test(i, data));
        }

        System.out.println();
    }

}
