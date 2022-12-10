package dz.me.dashboard.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.Ticket;
import dz.me.dashboard.services.WSService;

@Service
public class WSServiceImpl implements WSService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifyAppelTicket(Ticket ticket) {
        System.out.println("notifyAppelTicket" + ticket);
        messagingTemplate.convertAndSend("/file-attente/appel-ticket", ticket);
    }

    @Override
    public void notifyCountTicketInstance(int countTicketsInstance) {
        System.out.println("notifyCountTicketInstance" + countTicketsInstance);
        messagingTemplate.convertAndSend("/file-attente/count-instances-tickets", countTicketsInstance);
    }
}
