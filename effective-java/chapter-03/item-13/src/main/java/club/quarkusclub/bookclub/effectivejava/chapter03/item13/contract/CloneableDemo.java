package club.quarkusclub.bookclub.effectivejava.chapter03.item13.contract;

public final class CloneableDemo {
    public interface Describable {
        String describe();
    }

    public static final class MarkerOnly implements Cloneable {
    }

    public static Object unsupportedCopy() throws CloneNotSupportedException {
        return new CloneableDemo().clone();
    }

}
