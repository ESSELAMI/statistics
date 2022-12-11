package dz.me.dashboard.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import dz.me.dashboard.services.WSService;

@Service
public class WSServiceImpl implements WSService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifyCountTicketInstance(int countTicketsInstance) {
        System.out.println("notifyCountTicketInstance" + countTicketsInstance);
        messagingTemplate.convertAndSend("/file-attente/count-instances-tickets", countTicketsInstance);
    }
}
