package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Attempt B: getClass() makes a ColorPoint unusable as a Point")
class ColorPointGetClassTest {

    private final Point plain = new Point(0, 0);
    private final Point colored = new ColorPointGetClass(1, 2, Color.RED);

    @Test
    @DisplayName("The exception is one-sided: Point answers, ColorPoint throws")
    void exceptionIsNotSymmetric() {
        assertThat(plain.compareTo(colored)).isNegative();
        assertThatThrownBy(() -> colored.compareTo(plain))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("Code written for List<Point> works or fails depending on element order")
    void liskovSubstitutionIsViolated() {
        assertThat(Collections.max(List.of(colored, plain))).isSameAs(colored);
        assertThatThrownBy(() -> Collections.max(List.of(plain, colored)))
                .isInstanceOf(ClassCastException.class);
    }
}
