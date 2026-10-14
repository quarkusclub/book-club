package club.quarkusclub.bookclub.effectivejava.chapter03.item11.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 11 - Hashing follows the same canonical value as equality")
class CanonicalLabelTest {

    @Test
    @DisplayName("whitespace and case variants share one canonical value and hash")
    void canonicalValuesAgree() {
        CanonicalLabel a = new CanonicalLabel("  Book Club  ");
        CanonicalLabel b = new CanonicalLabel("book club");
        Set<CanonicalLabel> labels = new HashSet<>();
        labels.add(a);

        assertThat(a.value()).isEqualTo("book club");
        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(labels.contains(b)).isTrue();
    }
}
