package club.quarkusclub.bookclub.effectivejava.chapter03.item14.broken;

public record Rank(int value) implements Comparable<Rank> {

    @Override
    public int compareTo(Rank other) {
        return value > other.value ? 1 : 0;
    }
}
