package club.quarkusclub.bookclub.effectivejava.chapter03.item13.mutable;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Stack copying separates storage without implicitly cloning payloads")
class StackTest {
    @Test
    @DisplayName("Popping a shallow copy corrupts the original but an array copy isolates it")
    void cloneOwnsBackingArray() {
        var original = new Stack();
        original.push("top");
        original.shallowCopy().pop();
        assertThat(original.pop()).isNull();

        original.push("top");
        original.clone().pop();
        assertThat(original.pop()).isEqualTo("top");
    }

    @Test
    @DisplayName("Reference array clones share mutable elements until those are copied too")
    void arrayCloneSharesElements() {
        StringBuilder[] original = {new StringBuilder("A")};
        StringBuilder[] copy = original.clone();
        copy[0].append("B");
        assertThat(copy).isNotSameAs(original);
        assertThat(copy[0]).isSameAs(original[0]);
        assertThat(original[0].toString()).isEqualTo("AB");

        StringBuilder[] independent = {new StringBuilder(original[0])};
        independent[0].append("C");
        assertThat(original[0].toString()).isEqualTo("AB");
        assertThat(independent[0].toString()).isEqualTo("ABC");
    }

    @Test
    @DisplayName("A cloned stack still shares the identity of its mutable payload")
    void stackCloneSharesPayload() {
        var original = new Stack();
        original.push(new StringBuilder("A"));
        ((StringBuilder) original.clone().pop()).append("B");

        assertThat(original.pop().toString()).isEqualTo("AB");
    }

    @Test
    @DisplayName("Primitive array slots are independent while a matrix still shares rows")
    void primitiveArraysAndMatrices() {
        int[] numbers = {1};
        numbers.clone()[0] = 2;
        assertThat(numbers[0]).isEqualTo(1);
        int[][] matrix = {{1}};
        matrix.clone()[0][0] = 2;
        assertThat(matrix[0][0]).isEqualTo(2);
    }

    @Test
    @DisplayName("The minimal stack retains order when growing and rejects invalid operations")
    void stackBoundaries() {
        var stack = new Stack();
        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }
        for (int i = 4; i >= 0; i--) {
            assertThat(stack.pop()).isEqualTo(i);
        }
        assertThatThrownBy(stack::pop).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> stack.push(null)).isInstanceOf(NullPointerException.class);
    }
}
