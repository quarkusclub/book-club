package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Attempt A: comparing color only against other color points")
class ColorPointBrokenTransitivityTest {

    private final Point plain = new Point(1, 2);
    private final Point red = new ColorPointBrokenTransitivity(1, 2, Color.RED);
    private final Point blue = new ColorPointBrokenTransitivity(1, 2, Color.BLUE);

    @Test
    @DisplayName("red ties with plain, plain ties with blue, yet red and blue differ")
    void transitivityIsViolated() {
        assertThat(red.compareTo(plain)).isZero();
        assertThat(plain.compareTo(blue)).isZero();
        assertThat(red.compareTo(blue)).isNotZero();
    }

    @Test
    @DisplayName("The same three points give a TreeSet of different sizes depending on insertion order")
    void treeSetDependsOnInsertionOrder() {
        assertThat(new TreeSet<>(List.of(red, blue, plain))).hasSize(2);
        assertThat(new TreeSet<>(List.of(plain, red, blue))).hasSize(1);
    }
}
