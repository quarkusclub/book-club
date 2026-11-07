package club.quarkusclub.bookclub.effectivejava.chapter03.item14.contract;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Integer.signum;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CaseInsensitiveString obeys reflexivity, symmetry and transitivity")
class CompareToContractTest {

    private static final List<CaseInsensitiveString> SAMPLES = List.of(
            new CaseInsensitiveString("effective"),
            new CaseInsensitiveString("Java"),
            new CaseInsensitiveString("JAVA"),
            new CaseInsensitiveString("quarkus"));

    @Test
    @DisplayName("Reflexivity: every value compares as zero with itself")
    void reflexive() {
        for (var x : SAMPLES) {
            assertThat(x.compareTo(x)).isZero();
        }
    }

    @Test
    @DisplayName("Symmetry: swapping the operands flips the sign")
    void symmetric() {
        for (var x : SAMPLES) {
            for (var y : SAMPLES) {
                assertThat(signum(x.compareTo(y))).isEqualTo(-signum(y.compareTo(x)));
            }
        }
    }

    @Test
    @DisplayName("Transitivity: x > y and y > z imply x > z, and equal values compare alike")
    void transitive() {
        for (var x : SAMPLES) {
            for (var y : SAMPLES) {
                for (var z : SAMPLES) {
                    if (x.compareTo(y) > 0 && y.compareTo(z) > 0) {
                        assertThat(x.compareTo(z)).isPositive();
                    }
                    if (x.compareTo(y) == 0) {
                        assertThat(signum(x.compareTo(z))).isEqualTo(signum(y.compareTo(z)));
                    }
                }
            }
        }
    }
}
