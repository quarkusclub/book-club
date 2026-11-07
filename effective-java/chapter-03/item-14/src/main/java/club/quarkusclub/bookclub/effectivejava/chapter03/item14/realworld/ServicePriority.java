package club.quarkusclub.bookclub.effectivejava.chapter03.item14.realworld;

import java.util.Comparator;

public interface ServicePriority extends Comparable<ServicePriority> {

    int DEFAULT_PRIORITY = 1000;

    Comparator<ServicePriority> BY_PRIORITY = Comparator.comparingInt(ServicePriority::priority);

    default int priority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(ServicePriority other) {
        return Integer.compare(this.priority(), other.priority());
    }
}
