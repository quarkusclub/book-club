package club.quarkusclub.bookclub.effectivejava.chapter04.item15.arrays;

import java.util.List;

public final class SafeFormats {
    public static final List<String> VALUES = List.of("pdf", "epub");
    private static final String[] ARRAY = { "pdf", "epub" };

    private SafeFormats() {}

    public static String[] values() {
        return ARRAY.clone();
    }
}
