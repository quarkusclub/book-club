package club.quarkusclub.bookclub.effectivejava.chapter03.item13.inheritance;

public class InheritanceExample {
    public static final class Child extends InheritanceExample implements Cloneable {
        private final String label = "backend";

        public String label() {
            return label;
        }

        @Override
        public Child clone() {
            try {
                return (Child) super.clone();
            } catch (CloneNotSupportedException impossible) {
                throw new AssertionError(impossible);
            }
        }
    }

    public InheritanceExample brokenClone() {
        return new InheritanceExample();
    }

    @Override
    protected InheritanceExample clone() throws CloneNotSupportedException {
        return (InheritanceExample) super.clone();
    }

}
