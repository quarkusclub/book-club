package club.quarkusclub.bookclub.effectivejava.chapter03.item14.realworld;

public interface SubtractionServicePriority extends Comparable<SubtractionServicePriority> {

    int DEFAULT_PRIORITY = 1000;

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(SubtractionServicePriority other) {
        return this.priority() - other.priority();
    }
}
