package club.quarkusclub.bookclub.effectivejava.chapter03.item14.inheritance;

import java.awt.Color;

/**
 * Tentativa B: recusa tudo que não seja exatamente esta classe, o que o contrato permite. Mas
 * {@link Point} continua aceitando um ponto colorido, então a exceção é unilateral:
 * {@code p.compareTo(cp)} responde enquanto {@code cp.compareTo(p)} lança. Um ColorPointGetClass
 * não pode mais ser usado onde um Point é esperado: essa é a violação do princípio de Liskov.
 */
public class ColorPointGetClass extends Point {

    public final Color color;

    public ColorPointGetClass(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public int compareTo(Point o) {
        if (o.getClass() != getClass()) {
            throw new ClassCastException(o.getClass().getName() + " is not a " + getClass().getName());
        }
        var other = (ColorPointGetClass) o;
        int result = super.compareTo(other);
        return result != 0 ? result : Integer.compare(color.getRGB(), other.color.getRGB());
    }
}
