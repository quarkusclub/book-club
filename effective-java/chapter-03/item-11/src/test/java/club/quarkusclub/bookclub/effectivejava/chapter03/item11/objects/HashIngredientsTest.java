package club.quarkusclub.bookclub.effectivejava.chapter03.item11.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Hash ingredients use primitive wrappers and varargs")
class HashIngredientsTest {

    @Test
    @DisplayName("wrapper hashCode methods accept their corresponding primitive types")
    void primitiveHashMethodsNeedNoBoxing() {
        short areaCode = 313;
        int age = 42;
        boolean active = true;

        assertThat(Short.hashCode(areaCode)).isEqualTo(313);
        assertThat(Integer.hashCode(age)).isEqualTo(42);
        assertThat(Boolean.hashCode(active)).isEqualTo(Boolean.TRUE.hashCode());
    }

    @Test
    @DisplayName("boxing wraps a primitive and unboxing recovers it")
    void boxingAndUnboxingHaveOppositeDirections() {
        int age = 42;
        boolean active = true;
        Integer boxedAge = age;
        Boolean boxedActive = active;
        int unboxedAge = boxedAge;
        boolean unboxedActive = boxedActive;

        assertThat(boxedAge).isInstanceOf(Integer.class);
        assertThat(boxedActive).isInstanceOf(Boolean.class);
        assertThat(unboxedAge).isEqualTo(age);
        assertThat(unboxedActive).isEqualTo(active);
    }

    @Test
    @DisplayName("the varargs hash agrees with the hash of the equivalent boxed array")
    void objectsHashUsesAnObjectArray() {
        short lineNumber = 533;
        short prefix = 414;
        short areaCode = 313;
        Object[] boxedValues = {lineNumber, prefix, areaCode};

        assertThat(boxedValues).allSatisfy(value -> assertThat(value).isInstanceOf(Short.class));
        assertThat(Objects.hash(lineNumber, prefix, areaCode))
                .isEqualTo(Arrays.hashCode(boxedValues));
    }

    @Test
    @DisplayName("multiplication by an even number can discard a high bit during overflow")
    void oddMultiplierPreservesTheHighBit() {
        int highBit = Integer.MIN_VALUE;

        assertThat(32 * highBit).isEqualTo(32 * 0);
        assertThat(31 * highBit).isNotEqualTo(31 * 0);
    }
}
