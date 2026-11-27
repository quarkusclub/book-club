package club.quarkusclub.bookclub.effectivejava.chapter04.item15.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 15 - A public facade hides implementation types")
class TicketOfficeTest {
    @Test
    @DisplayName("clients issue a ticket without knowing the internal ticket type")
    void publicContract() {
        assertThat(new TicketOffice().issue(2, 7)).isEqualTo("R2-S7");
    }

    @Test
    @DisplayName("a test in the same package can exercise a package-private helper")
    void packagePrivateHelper() {
        assertThat(SeatFormatter.format(2, 7)).isEqualTo("R2-S7");
    }

    @Test
    @DisplayName("the private implementation rejects invalid seat coordinates")
    void invalidSeat() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TicketOffice().issue(0, 7));
    }
}
