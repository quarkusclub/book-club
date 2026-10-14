package club.quarkusclub.bookclub.effectivejava.chapter03.item11.objects;

import java.util.Locale;
import java.util.Objects;

/**
 * Equality is defined by stripped, locale-independent lowercase text, not equalsIgnoreCase.
 */
public record CanonicalLabel(String value) {

    public CanonicalLabel {
        value = Objects.requireNonNull(value).strip().toLowerCase(Locale.ROOT);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
