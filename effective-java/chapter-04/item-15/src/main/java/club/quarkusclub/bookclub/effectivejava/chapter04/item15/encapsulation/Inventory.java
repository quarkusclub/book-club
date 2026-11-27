package club.quarkusclub.bookclub.effectivejava.chapter04.item15.encapsulation;

public final class Inventory {
    private int available;

    public Inventory(int available) {
        if (available < 0) throw new IllegalArgumentException("Negative availability");
        this.available = available;
    }

    public void reserve(int quantity) {
        if (quantity <= 0 || quantity > available) {
            throw new IllegalArgumentException("Invalid reservation quantity");
        }
        available -= quantity;
    }

    public int available() { return available; }
}
