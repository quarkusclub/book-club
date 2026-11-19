package club.client;

import club.tickets.api.TicketOffice;

public final class Client {
    public String issue() { return new TicketOffice().issue(); }
}
