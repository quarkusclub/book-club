package club.quarkusclub.bookclub.effectivejava.chapter04.item15.encapsulation;

public final class ExposedInventory {
    public int available;

    public ExposedInventory(int available) {
        this.available = available;
    }
}
