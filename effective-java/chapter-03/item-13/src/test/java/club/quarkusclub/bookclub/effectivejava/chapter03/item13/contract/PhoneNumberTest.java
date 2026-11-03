package club.quarkusclub.bookclub.effectivejava.chapter03.item13.contract;

import club.quarkusclub.bookclub.effectivejava.chapter03.item13.mutable.Stack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PhoneNumber cloning preserves value and concrete class without construction")
class PhoneNumberTest {
    @Test
    @DisplayName("A covariant clone returns an equal but distinct PhoneNumber")
    void clonePreservesValue() {
        var original = new PhoneNumber(313, 414, 533);
        int before = PhoneNumber.constructorCalls();

        PhoneNumber copy = original.clone();

        assertThat(copy).isNotSameAs(original).isEqualTo(original);
        assertThat(copy.getClass()).isEqualTo(original.getClass());
        assertThat(copy.hashCode()).isEqualTo(original.hashCode());
        assertThat(PhoneNumber.constructorCalls()).isEqualTo(before);
    }

    @Test
    @DisplayName("A class inheriting Object equals need not equal its clone")
    void inheritedEqualsUsesIdentity() {
        var original = new Stack();

        assertThat(original.clone()).isNotSameAs(original).isNotEqualTo(original);
    }

    @Test
    @DisplayName("Ordinary construction still validates phone components")
    void constructorRejectsInvalidState() {
        assertThatThrownBy(() -> new PhoneNumber(-1, 414, 533))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(313, 414, 10_000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
