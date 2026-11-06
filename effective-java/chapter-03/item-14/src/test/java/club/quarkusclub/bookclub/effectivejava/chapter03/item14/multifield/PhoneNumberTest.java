package club.quarkusclub.bookclub.effectivejava.chapter03.item14.multifield;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DisplayName("Multi-field ordering compares the most significant field first")
class PhoneNumberTest {

    @Test
    @DisplayName("The area code decides before prefix and line number are consulted")
    void mostSignificantFieldFirst() {
        assertThat(new PhoneNumber(11, 9999, 9999).compareTo(new PhoneNumber(21, 0, 0))).isNegative();
        assertThat(new PhoneNumber(11, 3000, 9999).compareTo(new PhoneNumber(11, 4000, 0))).isNegative();
        assertThat(new PhoneNumber(11, 3000, 1).compareTo(new PhoneNumber(11, 3000, 2))).isNegative();
    }

    @Test
    @DisplayName("Hand-written compareTo and comparator construction methods produce the same order")
    void manualAndComparatorAgree() {
        int[][] numbers = {{21, 3000, 1}, {11, 4000, 2}, {11, 3000, 9}, {21, 1000, 5}, {11, 3000, 1}};
        var manual = new ArrayList<PhoneNumber>();
        var withComparator = new ArrayList<ComparatorPhoneNumber>();
        for (int[] n : numbers) {
            manual.add(new PhoneNumber(n[0], n[1], n[2]));
            withComparator.add(new ComparatorPhoneNumber(n[0], n[1], n[2]));
        }

        Collections.sort(manual);
        Collections.sort(withComparator);

        assertThat(manual).extracting(PhoneNumber::areaCode, PhoneNumber::prefix, PhoneNumber::lineNum)
                .containsExactlyElementsOf(withComparator.stream()
                        .map(p -> tuple(p.areaCode(), p.prefix(), p.lineNum()))
                        .toList());
        assertThat(manual.getFirst()).isEqualTo(new PhoneNumber(11, 3000, 1));
    }

    @Test
    @DisplayName("compareTo returns zero exactly when equals returns true")
    void consistentWithEquals() {
        var a = new PhoneNumber(11, 3000, 1);
        var b = new PhoneNumber(11, 3000, 1);
        var other = new PhoneNumber(11, 3000, 2);

        assertThat(a.compareTo(b)).isZero();
        assertThat(a).isEqualTo(b);
        assertThat(a.compareTo(other)).isNotZero();
        assertThat(a).isNotEqualTo(other);
    }
}
