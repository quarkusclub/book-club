package club.quarkusclub.bookclub.effectivejava.chapter03.item11.distribution;

import static org.assertj.core.api.Assertions.assertThat;

import club.quarkusclub.bookclub.effectivejava.chapter03.item11.contract.PhoneNumber;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Constant hashes are legal but distribute poorly")
class ConstantHashPhoneNumberTest {

    @Test
    @DisplayName("colliding unequal values coexist and equal values remain findable")
    void constantHashStillWorks() {
        ConstantHashPhoneNumber a = new ConstantHashPhoneNumber(313, 414, 533);
        ConstantHashPhoneNumber b = new ConstantHashPhoneNumber(314, 413, 533);
        ConstantHashPhoneNumber equalInstance = new ConstantHashPhoneNumber(313, 414, 533);
        Set<ConstantHashPhoneNumber> numbers = new HashSet<>(Set.of(a, b));
        Map<ConstantHashPhoneNumber, String> owners =
                new HashMap<>(Map.of(a, "First", b, "Second"));

        assertThat(a).isEqualTo(equalInstance).isNotEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(equalInstance.hashCode()).isEqualTo(b.hashCode());
        assertThat(numbers.contains(equalInstance)).isTrue();
        assertThat(numbers.add(equalInstance)).isFalse();
        assertThat(numbers.size()).isEqualTo(2);
        assertThat(owners.get(equalInstance)).isEqualTo("First");
        assertThat(owners.get(b)).isEqualTo("Second");
    }

    @Test
    @DisplayName("sixteen sample numbers target one bucket with a constant hash")
    void constantHashLosesDistribution() {
        long constantBuckets = IntStream.range(0, 16)
                .map(i -> new ConstantHashPhoneNumber(313, 414, i).hashCode())
                .map(ConstantHashPhoneNumberTest::bucketInTableOfSixteen)
                .distinct().count();
        long regularBuckets = IntStream.range(0, 16)
                .map(i -> new PhoneNumber(313, 414, i).hashCode())
                .map(ConstantHashPhoneNumberTest::bucketInTableOfSixteen)
                .distinct().count();

        assertThat(constantBuckets).isEqualTo(1);
        assertThat(regularBuckets).as("this sample and this table size only").isEqualTo(16);
    }

    /**
     * Illustrates OpenJDK's spread and bucket selection for a table of size sixteen.
     * This is an implementation illustration, not the HashMap API contract.
     */
    private static int bucketInTableOfSixteen(int hash) {
        return (hash ^ (hash >>> 16)) & 15;
    }
}
