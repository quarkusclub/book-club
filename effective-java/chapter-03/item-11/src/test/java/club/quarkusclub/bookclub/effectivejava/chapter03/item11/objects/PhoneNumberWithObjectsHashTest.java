package club.quarkusclub.bookclub.effectivejava.chapter03.item11.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Objects.hash preserves logical equality")
class PhoneNumberWithObjectsHashTest {

    @Test
    @DisplayName("equal phone numbers have equal Objects.hash values and work as map keys")
    void equalNumbersWorkAsKeys() {
        PhoneNumberWithObjectsHash a = new PhoneNumberWithObjectsHash(313, 414, 533);
        PhoneNumberWithObjectsHash b = new PhoneNumberWithObjectsHash(313, 414, 533);
        Map<PhoneNumberWithObjectsHash, String> owners = new HashMap<>();
        owners.put(a, "Book Club");

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(owners.get(b)).isEqualTo("Book Club");
    }

    @Test
    @DisplayName("all three components participate in equality")
    void differentNumbersRemainDifferent() {
        PhoneNumberWithObjectsHash number = new PhoneNumberWithObjectsHash(313, 414, 533);

        assertThat(number).isNotEqualTo(new PhoneNumberWithObjectsHash(314, 414, 533));
        assertThat(number).isNotEqualTo(new PhoneNumberWithObjectsHash(313, 415, 533));
        assertThat(number).isNotEqualTo(new PhoneNumberWithObjectsHash(313, 414, 534));
    }
}
