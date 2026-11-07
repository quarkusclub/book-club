package club.quarkusclub.bookclub.effectivejava.chapter03.item14.contract;

import java.util.Objects;

public record CaseInsensitiveString(String value) implements Comparable<CaseInsensitiveString> {

    public CaseInsensitiveString {
        Objects.requireNonNull(value, "value must not be null");
    }

    @Override
    public int compareTo(CaseInsensitiveString other) {
        return String.CASE_INSENSITIVE_ORDER.compare(value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveString other && value.equalsIgnoreCase(other.value);
    }

    /**
     * Converte cada char exatamente como {@link String#equalsIgnoreCase} faz, para que instâncias
     * iguais (e que se comparam como zero) sempre tenham o mesmo hash code.
     */
    @Override
    public int hashCode() {
        return value.chars()
                .map(c -> Character.toLowerCase(Character.toUpperCase(c)))
                .reduce(0, (hash, c) -> 31 * hash + c);
    }
}
