package club.quarkusclub.bookclub.effectivejava.chapter03.item13.alternatives;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Copy constructors and factories initialize independent final list references")
class AccountTest {
    @Test
    @DisplayName("Both copy operations preserve independent list ownership")
    void copiesOwnFinalList() {
        var original = new Account(List.of("primary"));
        var constructorCopy = new Account(original);
        var factoryCopy = Account.copyOf(original);
        constructorCopy.addLabel("constructor");
        factoryCopy.addLabel("factory");

        assertThat(original.labels()).containsExactly("primary");
        assertThat(constructorCopy.labels()).containsExactly("primary", "constructor");
        assertThat(factoryCopy.labels()).containsExactly("primary", "factory");
        assertThat(factoryCopy).isNotSameAs(original);
    }

    @Test
    @DisplayName("Accessors do not expose mutable storage and immutable strings may be shared")
    void stringsCanBeSharedSafely() {
        var original = new Account(List.of("primary"));
        var copy = Account.copyOf(original);

        assertThat(copy.labels().getFirst()).isSameAs(original.labels().getFirst());
        assertThatThrownBy(() -> copy.labels().add("external"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
