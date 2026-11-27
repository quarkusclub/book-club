package club.quarkusclub.bookclub.effectivejava.chapter04.item15.encapsulation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 15 - Encapsulation protects inventory invariants")
class InventoryTest {
    @Test
    @DisplayName("a public field allows a caller to create negative availability")
    void publicFieldBypassesValidation() {
        var inventory = new ExposedInventory(10);
        inventory.available = -100;
        assertThat(inventory.available).isNegative();
    }

    @Test
    @DisplayName("a reservation changes availability through the public contract")
    void validReservation() {
        var inventory = new Inventory(10);
        inventory.reserve(3);
        assertThat(inventory.available()).isEqualTo(7);
    }

    @Test
    @DisplayName("invalid reservations leave the inventory unchanged")
    void invalidReservations() {
        var inventory = new Inventory(10);
        assertThatIllegalArgumentException().isThrownBy(() -> inventory.reserve(11));
        assertThatIllegalArgumentException().isThrownBy(() -> inventory.reserve(0));
        assertThatIllegalArgumentException().isThrownBy(() -> inventory.reserve(-1));
        assertThat(inventory.available()).isEqualTo(10);
    }

    @Test
    @DisplayName("construction rejects negative availability")
    void invalidInitialState() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Inventory(-1));
    }
}
