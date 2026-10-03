package club.quarkusclub.bookclub.effectivejava.chapter03.item12.representation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 12 - Object.toString does not describe logical phone state")
class PhoneNumberWithoutToStringTest {

    @Test
    @DisplayName("the inherited representation consists of the class name and hexadecimal hash")
    void inheritedTextUsesClassAndHash() {
        PhoneNumberWithoutToString number = new PhoneNumberWithoutToString(313, 414, 533);
        String inherited = number.getClass().getName() + "@"
                + Integer.toHexString(number.hashCode());

        assertThat(number.toString()).isEqualTo(inherited);
        assertThat("Failed to connect to " + number)
                .isEqualTo("Failed to connect to " + inherited);
        assertThat(number.toString()).doesNotContain("313-414-0533");
    }
}
