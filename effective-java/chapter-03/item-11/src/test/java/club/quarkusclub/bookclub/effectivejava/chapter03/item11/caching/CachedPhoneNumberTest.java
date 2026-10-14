package club.quarkusclub.bookclub.effectivejava.chapter03.item11.caching;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Lazy caching reuses nonzero phone hashes")
class CachedPhoneNumberTest {

    @Test
    @DisplayName("repeated sequential calls reuse the first nonzero calculation")
    void nonzeroHashIsCalculatedOnce() {
        CachedPhoneNumber number = new CachedPhoneNumber(313, 414, 533);
        assertThat(number.hashComputations()).isZero();

        int first = number.hashCode();
        assertThat(first).isNotZero();
        for (int i = 0; i < 10; i++) {
            assertThat(number.hashCode()).isEqualTo(first);
        }
        assertThat(number.hashComputations()).isEqualTo(1);
    }

    @Test
    @DisplayName("a genuine zero hash is recalculated without violating the contract")
    void zeroHashIsRecalculated() {
        CachedPhoneNumber a = new CachedPhoneNumber(0, 0, 0);
        CachedPhoneNumber b = new CachedPhoneNumber(0, 0, 0);

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isZero();
        assertThat(a.hashCode()).isEqualTo(b.hashCode()).isZero();
        assertThat(a.hashComputations()).isEqualTo(2);
    }
}
