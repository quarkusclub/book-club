package club.quarkusclub.bookclub.effectivejava.chapter04.item15.views;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 15 - An unmodifiable view is not a snapshot")
class FormatCatalogTest {
    @Test
    @DisplayName("a view observes later internal changes while a snapshot stays stable")
    void viewAndSnapshot() {
        var catalog = new FormatCatalog();
        var view = catalog.view();
        var snapshot = catalog.snapshot();
        catalog.register("epub");
        assertThat(view).containsExactly("pdf", "epub");
        assertThat(snapshot).containsExactly("pdf");
    }

    @Test
    @DisplayName("neither a view nor a snapshot lets a caller change the catalog")
    void callerCannotWrite() {
        var catalog = new FormatCatalog();
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> catalog.view().add("exe"));
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> catalog.snapshot().add("exe"));
        assertThat(catalog.snapshot()).containsExactly("pdf");
    }
}
