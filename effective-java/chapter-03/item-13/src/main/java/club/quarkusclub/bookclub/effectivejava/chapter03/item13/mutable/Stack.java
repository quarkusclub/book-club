package club.quarkusclub.bookclub.effectivejava.chapter03.item13.mutable;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Stack implements Cloneable {
    private Object[] elements = new Object[4];
    private int size;

    public void push(Object element) {
        Objects.requireNonNull(element);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }
        elements[size++] = element;
    }

    public Object pop() {
        if (size == 0) {
            throw new NoSuchElementException("Empty stack");
        }
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    public Stack shallowCopy() {
        try {
            return (Stack) super.clone();
        } catch (CloneNotSupportedException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @Override
    public Stack clone() {
        Stack copy = shallowCopy();
        copy.elements = elements.clone();
        return copy;
    }

}
