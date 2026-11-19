package club.quarkusclub.bookclub.effectivejava.chapter04.item15.arrays;

public final class UnsafeFormats {
    public static final String[] VALUES = { "pdf", "epub" };

    private UnsafeFormats() {}

    public static String[] values() {
        return VALUES;
    }
}
