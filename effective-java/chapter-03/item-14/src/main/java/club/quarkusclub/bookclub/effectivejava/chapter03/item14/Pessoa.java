package club.quarkusclub.bookclub.effectivejava.chapter03.item14;

import java.util.Objects;

public record Pessoa(Integer age, String name) implements Comparable<Pessoa> {

    public Pessoa {

        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(age, "age must be not null");

        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }

        if (age < 0) {
            throw new IllegalArgumentException("age must be greater than 0");
        }
    }

    @Override
    public int compareTo(Pessoa o) {
        return age.compareTo(o.age);
    }

}
