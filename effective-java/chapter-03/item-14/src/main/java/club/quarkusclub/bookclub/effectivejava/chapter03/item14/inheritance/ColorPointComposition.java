package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import java.awt.Color;
import java.util.Comparator;
import java.util.Objects;

/**
 * A saída: composição em vez de herança. Um {@code ColorPointComposition} não é um {@link Point},
 * ele tem um, exposto por {@link #asPoint()}. Sem subtipo para defender, pode definir sua própria
 * ordenação natural sobre os dois componentes.
 */
public final class ColorPointComposition implements Comparable<ColorPointComposition> {

    private static final Comparator<ColorPointComposition> COMPARATOR =
            Comparator.comparing(ColorPointComposition::asPoint)
                    .thenComparingInt(cp -> cp.color.getRGB());

    private final Point point;
    private final Color color;

    public ColorPointComposition(int x, int y, Color color) {
        this.point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    public Point asPoint() {
        return point;
    }

    @Override
    public int compareTo(ColorPointComposition other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ColorPointComposition other
                && other.point.equals(point)
                && other.color.equals(color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, color);
    }
}
