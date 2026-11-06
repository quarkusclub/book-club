package club.quarkusclub.bookclub.effectivejava.chapter03.item14.broken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("A compareTo that breaks the contract breaks every class relying on it")
class RankTest {

    private static final List<Rank> SORTED = List.of(
            new Rank(1), new Rank(2), new Rank(3), new Rank(4), new Rank(5));

    @Test
    @DisplayName("Rank violates symmetry: 1 equals 2, yet 2 is greater than 1")
    void symmetryIsViolated() {
        assertThat(new Rank(1).compareTo(new Rank(2))).isZero();
        assertThat(new Rank(2).compareTo(new Rank(1))).isPositive();
    }

    @Test
    @DisplayName("Collections.binarySearch looks for 5 and lands on 3")
    void binarySearchReturnsTheWrongElement() {
        int index = Collections.binarySearch(SORTED, new Rank(5));

        assertThat(index).isEqualTo(2);
        assertThat(SORTED.get(index)).isEqualTo(new Rank(3));
        assertThat(Collections.binarySearch(List.of(1, 2, 3, 4, 5), 5)).isEqualTo(4);
    }

    @Test
    @DisplayName("TreeSet silently drops a distinct element")
    void treeSetLosesElements() {
        var set = new TreeSet<Rank>();
        set.add(new Rank(2));
        set.add(new Rank(1));

        assertThat(set).containsExactly(new Rank(2));
    }
}
