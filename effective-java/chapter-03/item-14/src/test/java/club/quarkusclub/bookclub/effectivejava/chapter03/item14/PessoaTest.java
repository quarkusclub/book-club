package club.quarkusclub.bookclub.effectivejava.chapter03.item14;

import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void test_comparable() {
        Pessoa a = new Pessoa(31, "John Doe");
        Pessoa b = new Pessoa(15, "John Evans");
        Pessoa c = new Pessoa(28, "Annie Frank");
        Pessoa d = new Pessoa(28, "Mark Little");

        int i = a.compareTo(b);
        System.out.printf("result: %d%n", i);

        int i1 = b.compareTo(c);
        System.out.printf("result: %d%n", i1);

        int i3 = a.compareTo(c);
        System.out.printf("result: %d%n", i3);

        int i2 = c.compareTo(d);
        System.out.printf("result: %d%n", i2);
    }

}