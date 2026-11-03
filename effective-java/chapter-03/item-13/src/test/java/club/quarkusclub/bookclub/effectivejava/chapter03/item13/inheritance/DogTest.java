package club.quarkusclub.bookclub.effectivejava.chapter03.item13.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cloning preserves the concrete subclass")
class DogTest {
    @Test
    @DisplayName("A cloned dog is a distinct object of the same class")
    void clonePreservesConcreteClass() {
        Dog original = new Dog("Buddy", "Golden Retriever");
        Dog copy = original.clone();
        assertThat(copy).isNotSameAs(original).isExactlyInstanceOf(Dog.class);
    }
}
