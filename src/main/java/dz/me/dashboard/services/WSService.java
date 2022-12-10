package dz.me.dashboard.services;

import dz.me.dashboard.entities.Ticket;

public interface WSService {

    public void notifyCountTicketInstance(int countTicketsInstance);

    public void notifyAppelTicket(Ticket ticket);
}
