package club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Inconsistent hashes break collection lookups")
class BrokenHashCodeTest {

    @Test
    @DisplayName("without an override the hash still comes from Object")
    void missingOverrideKeepsIdentityHash() {
        PhoneNumberWithoutHashCode a = new PhoneNumberWithoutHashCode(313, 414, 533);
        PhoneNumberWithoutHashCode b = new PhoneNumberWithoutHashCode(313, 414, 533);

        assertThat(a).isEqualTo(b).isNotSameAs(b);
        assertThat(a.hashCode()).isEqualTo(System.identityHashCode(a));
        assertThat(b.hashCode()).isEqualTo(System.identityHashCode(b));
    }

    @Test
    @DisplayName("controlled different hashes make HashSet and HashMap miss an equal instance")
    void differentHashesPreventLogicalLookup() {
        PhoneNumber number = new PhoneNumber(313, 414, 533);
        PhoneNumberBrokenHashCode original = new PhoneNumberBrokenHashCode(number, 1);
        PhoneNumberBrokenHashCode equalInstance = new PhoneNumberBrokenHashCode(number, 2);
        Set<PhoneNumberBrokenHashCode> numbers = new HashSet<>();
        Map<PhoneNumberBrokenHashCode, String> owners = new HashMap<>();
        numbers.add(original);
        owners.put(original, "Book Club");

        assertThat(original).isEqualTo(equalInstance);
        assertThat(original.hashCode()).isNotEqualTo(equalInstance.hashCode());
        assertThat(numbers.contains(equalInstance)).isFalse();
        assertThat(owners.get(equalInstance)).isNull();
        assertThat(numbers.add(equalInstance)).isTrue();
        assertThat(numbers.size()).isEqualTo(2);
    }
}
