package club.quarkusclub.bookclub.effectivejava.chapter03.item12.format;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 12 - Phone numbers have a specified textual format")
class PhoneNumberTest {

    @Test
    @DisplayName("the sample phone number uses the specified twelve-character format")
    void specifiedFormatIncludesAllComponents() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);

        assertThat(number.toString()).isEqualTo("313-414-0533").hasSize(12);
    }

    @Test
    @DisplayName("all three fields are padded with leading zeros")
    void shortFieldsAreZeroPadded() {
        assertThat(new PhoneNumber(3, 14, 123).toString()).isEqualTo("003-014-0123");
        assertThat(new PhoneNumber(0, 0, 0).toString()).isEqualTo("000-000-0000");
        assertThat(new PhoneNumber(999, 999, 9999).toString()).isEqualTo("999-999-9999");
    }

    @Test
    @DisplayName("programmatic access preserves numeric values without parsing text")
    void accessorsExposeTheLogicalState() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);

        assertThat(number.areaCode()).isEqualTo((short) 313);
        assertThat(number.prefix()).isEqualTo((short) 414);
        assertThat(number.lineNumber()).isEqualTo((short) 533);
        assertThat(new PhoneNumber(number.areaCode(), number.prefix(), number.lineNumber()))
                .isEqualTo(number).hasSameHashCodeAs(number);
    }

    @Test
    @DisplayName("changing the default formatting locale keeps the documented decimal digits")
    void formatIsIndependentOfDefaultLocale() {
        Locale previous = Locale.getDefault(Locale.Category.FORMAT);
        try {
            Locale.setDefault(Locale.Category.FORMAT, Locale.forLanguageTag("ar"));
            assertThat(new PhoneNumber(313, 414, 533).toString()).isEqualTo("313-414-0533");
        } finally {
            Locale.setDefault(Locale.Category.FORMAT, previous);
        }
    }

    @Test
    @DisplayName("values outside the documented field widths are rejected before conversion")
    void invalidFieldsCannotBreakTheFormat() {
        assertThatThrownBy(() -> new PhoneNumber(-1, 414, 533))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(1000, 414, 533))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(313, -1, 533))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(313, 1000, 533))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(313, 414, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PhoneNumber(313, 414, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
