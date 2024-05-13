package hr.fer.zemris.ooup.lab2.model;

import java.lang.reflect.Constructor;

public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name) {
        try {
            @SuppressWarnings("unchecked")
            Class<Animal> animalClass = (Class<Animal>) Class.forName(
                    "hr.fer.zemris.ooup.lab2.model.plugins." + animalKind
            );

            Constructor<?> constructor = animalClass.getConstructor(String.class);
            return (Animal) constructor.newInstance(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
