package club.quarkusclub.bookclub.effectivejava.chapter03.item13.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("The super clone chain preserves a subclass runtime type")
class InheritanceExampleTest {
    @Test
    @DisplayName("Direct base construction loses the child while super clone retains it")
    void superClonePreservesSubtype() {
        var original = new InheritanceExample.Child();

        assertThat(original.brokenClone()).isExactlyInstanceOf(InheritanceExample.class);
        assertThatThrownBy(() -> InheritanceExample.Child.class.cast(original.brokenClone()))
                .isInstanceOf(ClassCastException.class);
        var copy = (InheritanceExample.Child) original.clone();
        assertThat(copy).isNotSameAs(original).isExactlyInstanceOf(original.getClass());
        assertThat(copy.label()).isEqualTo("backend");
    }
}
