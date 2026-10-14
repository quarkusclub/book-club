package club.quarkusclub.bookclub.effectivejava.chapter03.item11.caching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - An immutable document can reuse an expensive hash traversal")
class DocumentTest {

    @Test
    @DisplayName("one thousand paragraphs are traversed once across repeated sequential calls")
    void largeDocumentIsTraversedOnce() {
        List<String> paragraphs = IntStream.range(0, 1000)
                .mapToObj(i -> "Paragraph " + i).toList();
        Document document = new Document(paragraphs);
        assertThat(document.hashComputations()).isZero();

        int first = document.hashCode();
        assertThat(first).isNotZero();
        for (int i = 0; i < 100; i++) {
            assertThat(document.hashCode()).isEqualTo(first);
        }
        assertThat(document.hashComputations()).isEqualTo(1);
        assertThat(document).isEqualTo(new Document(paragraphs));
        assertThat(first).isEqualTo(new Document(paragraphs).hashCode());
    }

    @Test
    @DisplayName("mutating the caller's list cannot change equality or the cached hash")
    void copyProtectsCachedState() {
        List<String> input = new ArrayList<>(List.of("First", "Second"));
        Document document = new Document(input);
        int first = document.hashCode();

        input.add("Changed outside");

        assertThat(document).isEqualTo(new Document(List.of("First", "Second")));
        assertThat(document.hashCode()).isEqualTo(first);
        assertThat(document.hashComputations()).isEqualTo(1);
    }

    @Test
    @DisplayName("reordering paragraphs changes logical equality")
    void paragraphOrderIsSignificant() {
        Document a = new Document(List.of("First", "Second"));
        Document b = new Document(List.of("Second", "First"));

        assertThat(a).isNotEqualTo(b);
    }
}
