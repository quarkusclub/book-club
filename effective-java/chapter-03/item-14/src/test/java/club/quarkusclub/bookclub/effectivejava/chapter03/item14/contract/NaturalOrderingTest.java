package club.quarkusclub.bookclub.effectivejava.chapter03.item14.contract;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Comparable adds a natural ordering that Object does not provide")
class NaturalOrderingTest {

    @Test
    @DisplayName("Object declares equals but not compareTo")
    void compareToDoesNotLiveInObject() {
        assertThat(Object.class.getMethods()).extracting(Method::getName)
                .contains("equals")
                .doesNotContain("compareTo");
        assertThat(Comparable.class).isAssignableFrom(CaseInsensitiveString.class);
    }

    @Test
    @DisplayName("Generic algorithms sort, search and pick extremes without extra code")
    void genericAlgorithmsUseTheNaturalOrdering() {
        var words = new ArrayList<>(List.of(
                new CaseInsensitiveString("quarkus"),
                new CaseInsensitiveString("Java"),
                new CaseInsensitiveString("effective")));

        Collections.sort(words);

        assertThat(words).extracting(CaseInsensitiveString::value)
                .containsExactly("effective", "Java", "quarkus");
        assertThat(Collections.binarySearch(words, new CaseInsensitiveString("JAVA"))).isEqualTo(1);
        assertThat(Collections.max(words).value()).isEqualTo("quarkus");
    }

    @Test
    @DisplayName("A sorted collection rejects elements without a natural ordering")
    void sortedCollectionsRequireComparable() {
        var set = new TreeSet<Object>();

        assertThatThrownBy(() -> set.add(new Object()))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("equals answers false for another type, compareTo may throw ClassCastException")
    @SuppressWarnings({"rawtypes", "unchecked"})
    void compareToNeedNotWorkAcrossTypes() {
        var cis = new CaseInsensitiveString("java");
        Comparable raw = cis;

        assertThat(cis.equals("java")).isFalse();
        assertThatThrownBy(() -> raw.compareTo("java"))
                .isInstanceOf(ClassCastException.class);
    }
}
