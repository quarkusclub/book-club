package club.quarkusclub.bookclub.effectivejava.chapter03.item11.distribution;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - String collisions do not imply equality")
class StringCollisionTest {

    @Test
    @DisplayName("Aa and BB collide but remain different keys")
    void differentStringsShareAHash() {
        assertThat("Aa").isNotEqualTo("BB");
        assertThat("Aa".hashCode()).isEqualTo("BB".hashCode());
        Set<String> values = new HashSet<>();
        values.add("Aa");
        values.add("BB");
        assertThat(values.size()).isEqualTo(2);
    }
}
