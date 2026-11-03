package club.quarkusclub.bookclub.effectivejava.chapter03.item13.inheritance;

public class Animal implements Cloneable {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public Animal clone() {
        try {
            return (Animal) super.clone();
        } catch (CloneNotSupportedException impossible) {
            throw new AssertionError(impossible);
        }
    }
}
