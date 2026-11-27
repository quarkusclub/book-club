package club.quarkusclub.bookclub.effectivejava.chapter04.item15.facade;

final class SeatFormatter {
    private SeatFormatter() {}

    static String format(int row, int number) {
        return "R" + row + "-S" + number;
    }
}
