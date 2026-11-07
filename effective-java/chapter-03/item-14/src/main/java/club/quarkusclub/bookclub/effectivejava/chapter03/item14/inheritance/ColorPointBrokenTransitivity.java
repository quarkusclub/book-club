package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import java.awt.Color;

/**
 * Tentativa A: compara cores só quando o outro lado também é um ponto colorido e, contra um
 * {@link Point} comum, compara apenas a posição. Um ponto sem cor empata com um ponto vermelho e
 * com um azul que não empatam entre si, o que quebra a transitividade.
 */
public class ColorPointBrokenTransitivity extends Point {

    public final Color color;

    public ColorPointBrokenTransitivity(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public int compareTo(Point o) {
        int result = super.compareTo(o);
        if (result == 0 && o instanceof ColorPointBrokenTransitivity other) {
            result = Integer.compare(color.getRGB(), other.color.getRGB());
        }
        return result;
    }
}
