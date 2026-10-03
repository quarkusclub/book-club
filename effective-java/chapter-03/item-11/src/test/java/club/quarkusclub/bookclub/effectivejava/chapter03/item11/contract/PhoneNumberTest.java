package club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Equal phone numbers agree on hash codes")
class PhoneNumberTest {

    @Test
    @DisplayName("equal values have equal hashes on repeated calls")
    void equalNumbersHaveEqualHashes() {
        PhoneNumber a = new PhoneNumber(313, 414, 533);
        PhoneNumber b = new PhoneNumber(313, 414, 533);

        assertThat(a).isNotSameAs(b).isEqualTo(b);
        for (int i = 0; i < 5; i++) {
            assertThat(a.hashCode()).isEqualTo(b.hashCode());
        }
    }

    @Test
    @DisplayName("these two concrete values have different hashes in this implementation")
    void theseDifferentNumbersHaveDifferentHashes() {
        PhoneNumber a = new PhoneNumber(313, 414, 533);
        PhoneNumber b = new PhoneNumber(314, 413, 533);

        assertThat(a).isNotEqualTo(b);
        assertThat(a.hashCode())
                .as("an observed example of distribution, not a universal contract requirement")
                .isNotEqualTo(b.hashCode());
    }

    @Test
    @DisplayName("different phone numbers can collide and remain distinct in a HashSet")
    void aCollisionDoesNotMeanEquality() {
        PhoneNumber a = new PhoneNumber(313, 414, 533);
        PhoneNumber b = new PhoneNumber(313, 413, 564);

        assertThat(a).isNotEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        Set<PhoneNumber> numbers = new HashSet<>();
        numbers.add(a);
        numbers.add(b);
        assertThat(numbers.size()).isEqualTo(2);
        assertThat(numbers.contains(new PhoneNumber(313, 413, 564))).isTrue();
    }

    @Test
    @DisplayName("HashSet and HashMap find a new but equal phone number")
    void equalInstanceFindsStoredEntries() {
        PhoneNumber original = new PhoneNumber(313, 414, 533);
        PhoneNumber equalInstance = new PhoneNumber(313, 414, 533);
        Set<PhoneNumber> numbers = new HashSet<>();
        Map<PhoneNumber, String> owners = new HashMap<>();
        numbers.add(original);
        owners.put(original, "Book Club");

        assertThat(numbers.contains(equalInstance)).isTrue();
        assertThat(owners.get(equalInstance)).isEqualTo("Book Club");
        assertThat(numbers.add(equalInstance)).isFalse();
    }

    @Test
    @DisplayName("changing any significant field changes equality and this example hash")
    void allSignificantFieldsParticipate() {
        PhoneNumber original = new PhoneNumber(313, 414, 533);
        for (PhoneNumber changed : Set.of(
                new PhoneNumber(314, 414, 533),
                new PhoneNumber(313, 415, 533),
                new PhoneNumber(313, 414, 534))) {
            assertThat(changed).isNotEqualTo(original);
            assertThat(changed.hashCode())
                    .as("distribution for these specific single-field changes")
                    .isNotEqualTo(original.hashCode());
        }
        assertThat(original.equals(null)).isFalse();
        assertThat(original.equals("313-414-0533")).isFalse();
    }
}
