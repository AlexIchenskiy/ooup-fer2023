package hr.fer.zemris.ooup.lab2.model.demo;

import hr.fer.zemris.ooup.lab2.model.Animal;
import hr.fer.zemris.ooup.lab2.model.AnimalFactory;

import java.util.Scanner;

public class AnimalFactoryDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("Welcome to the animal factory. Write 'exit' to quit.");
        while (true) {
            System.out.println("Please enter an Animal kind and name, divided by space.");
            input = sc.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            try {
                String[] data = input.split(" ");
                Animal animal = AnimalFactory.newInstance(data[0], data[1]);
                animal.animalPrintMenu();
                animal.animalPrintGreeting();
            } catch (Exception e) {
                System.out.println("Could not create an animal. Reason: \n" + e.getMessage());
            }
        }
    }

}
