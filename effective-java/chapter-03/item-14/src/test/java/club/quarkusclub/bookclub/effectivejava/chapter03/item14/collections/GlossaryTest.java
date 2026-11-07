package club.quarkusclub.bookclub.effectivejava.chapter03.item14.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TreeSet keeps elements ordered and unique through compareTo")
class GlossaryTest {

    @Test
    @DisplayName("Terms come out sorted without ever calling sort")
    void treeSetSortsOnInsertion() {
        var glossary = new Glossary();
        glossary.add("TreeSet");
        glossary.add("comparable");
        glossary.add("Item");

        assertThat(glossary.terms()).containsExactly("comparable", "Item", "TreeSet");
        assertThat(glossary.first()).isEqualTo("comparable");
    }

    @Test
    @DisplayName("Duplicates are detected by compareTo, not by equals")
    void treeSetDeduplicatesWithCompareTo() {
        var glossary = new Glossary();

        assertThat(glossary.add("Java")).isTrue();
        assertThat(glossary.add("JAVA")).isFalse();
        assertThat(glossary.terms()).containsExactly("Java");
    }

    @Test
    @DisplayName("BigDecimal is inconsistent with equals, so HashSet and TreeSet disagree")
    void inconsistentWithEquals() {
        var values = List.of(new BigDecimal("1.0"), new BigDecimal("1.00"));

        assertThat(new HashSet<>(values)).hasSize(2);
        assertThat(new TreeSet<>(values)).hasSize(1);
    }
}
