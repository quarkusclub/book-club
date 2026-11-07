package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Composition: a ColorPoint that has a Point instead of being one")
class ColorPointCompositionTest {

    @Test
    @DisplayName("Orders by position, then color, and keeps every distinct point in any order")
    void ordersBothComponents() {
        var red = new ColorPointComposition(1, 2, Color.RED);
        var blue = new ColorPointComposition(1, 2, Color.BLUE);
        var origin = new ColorPointComposition(0, 0, Color.RED);

        assertThat(red.compareTo(blue)).isNotZero();
        assertThat(new TreeSet<>(List.of(red, blue, origin))).hasSize(3).first().isEqualTo(origin);
        assertThat(new TreeSet<>(List.of(origin, blue, red))).hasSize(3);
    }

    @Test
    @DisplayName("The Point view still takes part in Point orderings")
    void viewAsPoint() {
        var colored = new ColorPointComposition(1, 2, Color.RED);

        assertThat(colored.asPoint().compareTo(new Point(1, 2))).isZero();
        assertThat(new TreeSet<>(List.of(new Point(0, 0), colored.asPoint()))).hasSize(2);
    }
}
