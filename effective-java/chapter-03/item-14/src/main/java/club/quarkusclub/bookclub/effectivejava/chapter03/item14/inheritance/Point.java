package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

/**
 * Ponto instanciável ordenado por x e depois por y. Sozinho é consistente com equals; o problema
 * começa quando uma subclasse adiciona um componente de valor (veja {@code ColorPoint*}).
 */
public class Point implements Comparable<Point> {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point other) {
        int result = Integer.compare(x, other.x);
        return result != 0 ? result : Integer.compare(y, other.y);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Point other && other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "Point(%d, %d)".formatted(x, y);
    }
}
