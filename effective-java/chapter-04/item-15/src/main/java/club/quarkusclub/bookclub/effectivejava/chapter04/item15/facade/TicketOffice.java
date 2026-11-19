package club.quarkusclub.bookclub.effectivejava.chapter04.item15.facade;

public final class TicketOffice {
    public String issue(int row, int number) { return new Ticket(row, number).label(); }

    private static final class Ticket {
        private final int row;
        private final int number;

        private Ticket(int row, int number) {
            if (row <= 0 || number <= 0) {
                throw new IllegalArgumentException("Seat coordinates must be positive");
            }
            this.row = row;
            this.number = number;
        }

        private String label() { return SeatFormatter.format(row, number); }
    }
}
