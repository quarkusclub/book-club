package club.quarkusclub.bookclub.effectivejava.chapter04.item15.access;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 15 - Package access includes protected members")
class AccessRulesTest {
    @Test
    @DisplayName("a same-package caller can access package-private and protected members")
    void samePackageAccess() {
        var rules = new AccessRules();
        assertThat(rules.packageCode()).isEqualTo("private");
        assertThat(rules.protectedCode()).isEqualTo("protected");
        assertThat(rules.publicCode()).isEqualTo("public");
    }
}
