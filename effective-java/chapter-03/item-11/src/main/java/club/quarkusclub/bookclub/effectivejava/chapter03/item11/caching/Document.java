package club.quarkusclub.bookclub.effectivejava.chapter03.item11.caching;

import java.util.List;

/**
 * Immutable logical state makes lazy caching safe for equality.
 * The counter measures sequential traversals; concurrent calls may calculate more than once.
 */
public final class Document {

    private final List<String> paragraphs;
    private int hashCode;
    private int hashComputations;

    public Document(List<String> paragraphs) {
        this.paragraphs = List.copyOf(paragraphs);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Document other && paragraphs.equals(other.paragraphs);
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            hashComputations++;
            result = paragraphs.hashCode();
            hashCode = result;
        }
        return result;
    }

    int hashComputations() {
        return hashComputations;
    }
}
