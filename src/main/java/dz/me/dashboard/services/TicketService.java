package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Ticket;

public interface TicketService {

    public Ticket appelTicketSuivant(UUID idService, String idLastTicket, UUID IdUser, UUID guichet, String traitement);

    public void finTraitementTicket(UUID idService, String idLastTickt, UUID IdUser, UUID guichet,
            String traitement);

    public void deleteOld();

    public Ticket imprimerTicketSuivant(UUID idService, int prioritaire);

    public Ticket dernierTicketAppeleService(UUID idService);

    public Ticket dernierTicketAppeleServiceCommun(UUID serviceId);

    public List<Ticket> historiqueTicketServiceCommun(UUID serviceId, int limit);

    public Ticket tansfererTicket(UUID IdUser, UUID idServiceTransferer, String ticket, UUID monServiceId);

    public int nombreTicketInstancesService(UUID idService);

    public int nombreTicketInstancesServiceCommun(UUID idService);

    public int delaisAttenteMoyen(UUID idService);

    public int delaisTraitementMoyen(UUID idService);

    public String lastServiceByGuichetUserIdService(UUID idGuichet, UUID IdUserAppel, UUID IdService);

    public int nbrTicketByUser(UUID IdUserTrait);

    public Optional<Ticket> lastTicketByGuichet(String idGuichet);

}
