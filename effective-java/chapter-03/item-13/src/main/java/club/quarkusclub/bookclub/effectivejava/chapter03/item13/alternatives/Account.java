package club.quarkusclub.bookclub.effectivejava.chapter03.item13.alternatives;

import java.util.ArrayList;
import java.util.List;

public final class Account {
    private final List<String> labels;

    public Account(List<String> labels) {
        this.labels = new ArrayList<>(labels);
    }

    public Account(Account source) {
        this(source.labels);
    }

    public static Account copyOf(Account source) {
        return new Account(source);
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public List<String> labels() {
        return List.copyOf(labels);
    }

}
