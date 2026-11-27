package club.quarkusclub.bookclub.effectivejava.chapter04.item15.arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 15 - Final references do not make arrays immutable")
class FormatsTest {
    @Test
    @DisplayName("a public final array and a getter expose the same mutable storage")
    void mutableArrayLeaks() {
        String original = UnsafeFormats.VALUES[0];
        try {
            UnsafeFormats.values()[0] = "exe";
            assertThat(UnsafeFormats.VALUES[0]).isEqualTo("exe");
            assertThat(UnsafeFormats.values()).isSameAs(UnsafeFormats.VALUES);
        } finally {
            UnsafeFormats.VALUES[0] = original;
        }
    }

    @Test
    @DisplayName("changing a defensive array copy leaves internal storage unchanged")
    void defensiveCopy() {
        String[] copy = SafeFormats.values();
        copy[0] = "exe";
        assertThat(copy).containsExactly("exe", "epub");
        assertThat(SafeFormats.values()).containsExactly("pdf", "epub");
    }

    @Test
    @DisplayName("an unmodifiable list of immutable elements is safe to expose")
    void immutableConstant() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> SafeFormats.VALUES.set(0, "exe"));
        assertThat(SafeFormats.VALUES).containsExactly("pdf", "epub");
    }
}
