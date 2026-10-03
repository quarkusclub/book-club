package club.quarkusclub.bookclub.effectivejava.chapter03.item12.format;

import java.util.Objects;

public record Potion(int number, String type, String smell, String look) {

    public Potion {
        Objects.requireNonNull(type);
        Objects.requireNonNull(smell);
        Objects.requireNonNull(look);
    }

    /**
     * Returns a brief description. Its exact format is unspecified and subject to change.
     * A typical description is "[Potion #9: type=love, smell=turpentine, look=india ink]".
     * Use the component accessors for programmatic access.
     */
    @Override
    public String toString() {
        return "[Potion #" + number + ": type=" + type
                + ", smell=" + smell + ", look=" + look + "]";
    }
}
