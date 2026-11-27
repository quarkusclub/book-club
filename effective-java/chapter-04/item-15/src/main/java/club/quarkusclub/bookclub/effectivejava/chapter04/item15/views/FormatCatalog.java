package club.quarkusclub.bookclub.effectivejava.chapter04.item15.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FormatCatalog {
    private final List<String> formats = new ArrayList<>(List.of("pdf"));

    public void register(String format) {
        formats.add(format);
    }

    public List<String> view() {
        return Collections.unmodifiableList(formats);
    }

    public List<String> snapshot() {
        return List.copyOf(formats);
    }
}
