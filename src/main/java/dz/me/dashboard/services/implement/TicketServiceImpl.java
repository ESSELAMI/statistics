package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.ArchiveTraitement;
import dz.me.dashboard.entities.Ticket;
import dz.me.dashboard.exceptions.ResourceNotFoundException;
import dz.me.dashboard.repositories.ArchiveTraitementRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.TicketRepository;
import dz.me.dashboard.services.GuichetService;
import dz.me.dashboard.services.TicketService;
import dz.me.dashboard.services.UserService;

/**
 *
 * @author Tarek Mekriche
 */
@Service
public class TicketServiceImpl implements TicketService {
    /* */
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private GuichetService guichetService;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    UserService userService;

    @Autowired
    ArchiveTraitementRepository archiveTraitementRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUICHET') ")
    public Ticket appelTicketSuivant(UUID idService, String idLastTickt, UUID IdUser, UUID guichet, String traitement) {
        Integer idLastTicket = Integer.parseInt(idLastTickt.substring(0, idLastTickt.length() - 1));

        if (idLastTicket != 0) {

            try {
                Ticket ticketTraiter = ticketRepository.findByTicketAndServiceAndSysDate(idService.toString(),
                        idLastTicket.toString());
                if (ticketTraiter.getDateTraitement() == null) {
                    ticketTraiter.setDateTraitement(new Date());
                    ticketTraiter.setGuichet(guichetService.findGuichetById(guichet).get());
                    ticketTraiter.setUserTrait(userService.findById(IdUser).get());
                    ArchiveTraitement archiveTraitement = new ArchiveTraitement();
                    archiveTraitement.setIdTicket(ticketTraiter.getId());
                    archiveTraitement.setTicket(ticketTraiter.getTicket());
                    archiveTraitement.setPseudoTicket(ticketTraiter.getPseudoTicket());
                    archiveTraitement.setTransferred(ticketTraiter.isTransferred());
                    archiveTraitement.setService(ticketTraiter.getService());
                    archiveTraitement.setDateEdition(ticketTraiter.getDateEdition());
                    archiveTraitement.setDateAppel(ticketTraiter.getDateAppel());
                    archiveTraitement.setDateTraitement(ticketTraiter.getDateTraitement());
                    archiveTraitement.setIdClient(ticketTraiter.getIdClient());
                    archiveTraitement.setUserAppel(ticketTraiter.getUserAppel());
                    archiveTraitement.setUserTrait(ticketTraiter.getUserTrait());
                    archiveTraitement.setUserTransfer(ticketTraiter.getUserTransfer());
                    archiveTraitement.setGuichet(ticketTraiter.getGuichet());
                    archiveTraitement.setTraitement(traitement);
                    archiveTraitementRepository.save(archiveTraitement);
                    ticketRepository.save(ticketTraiter);
                }
            } catch (Exception e) {
                throw new ResourceNotFoundException(
                        "id-last-ticket-traiter inexistant");
            }
        }
        Integer minTicket = 0;
        try {
            minTicket = ticketRepository.minTicketAppeler(idService.toString());
        } catch (Exception e) {
            minTicket = 0;
        }
        if (minTicket == 0) {
            throw new ResourceNotFoundException(
                    "pas de ticket a appeler ");
        }
        Ticket ticket = ticketRepository.findByTicketAndServiceAndSysDate(idService.toString(),
                minTicket.toString());

        ticket.setDateAppel(new Date());
        ticket.setGuichet(guichetService.findGuichetById(guichet).get());
        ticket.setUserAppel(userService.findById(IdUser).get());
        ticket = ticketRepository.save(ticket);
        ticket.setTicket(ticket.getTicket() + "" + ticket.getService().getServiceLettre());
        return ticket;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUICHET') ")
    public void finTraitementTicket(UUID idService, String idLastTickt, UUID IdUser, UUID guichet,
            String traitement) {
        Integer idLastTicket = Integer.parseInt(idLastTickt.substring(0, idLastTickt.length() - 1));

        if (idLastTicket != 0) {

            try {
                Ticket ticketTraiter = ticketRepository.findByTicketAndServiceAndSysDate(idService.toString(),
                        idLastTicket.toString());
                if (ticketTraiter.getDateTraitement() == null) {
                    ticketTraiter.setDateTraitement(new Date());
                    ticketTraiter.setGuichet(guichetService.findGuichetById(guichet).get());
                    ticketTraiter.setUserTrait(userService.findById(IdUser).get());
                    ArchiveTraitement archiveTraitement = new ArchiveTraitement();
                    archiveTraitement.setIdTicket(ticketTraiter.getId());
                    archiveTraitement.setTicket(ticketTraiter.getTicket());
                    archiveTraitement.setPseudoTicket(ticketTraiter.getPseudoTicket());
                    archiveTraitement.setTransferred(ticketTraiter.isTransferred());
                    archiveTraitement.setService(ticketTraiter.getService());
                    archiveTraitement.setDateEdition(ticketTraiter.getDateEdition());
                    archiveTraitement.setDateAppel(ticketTraiter.getDateAppel());
                    archiveTraitement.setDateTraitement(ticketTraiter.getDateTraitement());
                    archiveTraitement.setIdClient(ticketTraiter.getIdClient());
                    archiveTraitement.setUserAppel(ticketTraiter.getUserAppel());
                    archiveTraitement.setUserTrait(ticketTraiter.getUserTrait());
                    archiveTraitement.setUserTransfer(ticketTraiter.getUserTransfer());
                    archiveTraitement.setGuichet(ticketTraiter.getGuichet());
                    archiveTraitement.setTraitement(traitement);
                    archiveTraitementRepository.save(archiveTraitement);
                    ticketRepository.save(ticketTraiter);
                }
            } catch (Exception e) {
                throw new ResourceNotFoundException(
                        "id-last-ticket-traiter inexistant");
            }
        }

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GUICHET') ")
    public Ticket tansfererTicket(UUID IdUser, UUID idServiceTransferer, String pseudoTicket, UUID monServiceId) {
        // pseudoTicket=pseudoTicket.substring(0, pseudoTicket.length() - 1);
        Ticket t = ticketRepository.findByTicketAndServiceAndSysDate(monServiceId.toString(),
                pseudoTicket.substring(0, pseudoTicket.length() - 1));
        if (t == null) {
            throw new ResourceNotFoundException(
                    "Ticket non trouvé");
        }
        Optional<Ticket> t2 = ticketRepository.findByIdPseudoTicket(t.getId());

        if (t2.isPresent()) {
            throw new ResourceNotFoundException(
                    "Ticket deja envoyé");
        }
        if (t.getDateAppel() == null) {

            throw new ResourceNotFoundException(
                    "Ticket non appelé");

        }
        if (t.getDateTraitement() != null) {

            throw new ResourceNotFoundException(
                    "Ticket deja traité");

        }

        Ticket ticket = new Ticket();
        ticket.setService(serviceRepository.findById(idServiceTransferer).get());
        ticket.setTransferred(true);
        ticket.setPseudoTicket(pseudoTicket);
        ticket.setPseudoFromService(serviceRepository.findById(monServiceId).get());
        ticket.setIdPseudoTicket(t.getId());

        Long lastTicketAppeler = ticketRepository.findLastTicketEditer(idServiceTransferer.toString());
        ticket.setTicket(lastTicketAppeler.toString());
        ticket.setUserTransfer(userService.findById(IdUser).get());
        ticket = ticketRepository.save(ticket);

        ticket.setTicket(ticket.getTicket() + ticket.getService().getServiceLettre());
        // ticket.setTicket(ticket.getTicket() +
        // ticket.getService().getServiceLettre());
        return ticket;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public Ticket imprimerTicketSuivant(UUID idService, int prioritaire) {
        Ticket ticket = new Ticket();
        ticket.setService(serviceRepository.findById(idService).get());
        ticket.setTransferred(false);
        Long lastTicketAppeler = ticketRepository.findLastTicketEditer(idService.toString());
        ticket.setTicket(lastTicketAppeler.toString());
        ticket.setPrioritaire(prioritaire);
        ticket = ticketRepository.save(ticket);
        ticket.setTicket(ticket.getTicket() + ticket.getService().getServiceLettre());
        return ticket;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public int nombreTicketInstancesService(UUID idService) {
        return ticketRepository.nombreTicketInstanceService(idService.toString());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public int nombreTicketInstancesServiceCommun(UUID idService) {
        // List<ServiceCommun> listServiceCommun =
        // serviceCommunService.serviceCommunByIdService(idService);
        List<String> listIdService = new ArrayList<>();

        Optional<dz.me.dashboard.entities.Service> monService = serviceRepository.findById(idService);
        if (!monService.isPresent()) {
            throw new ResourceNotFoundException(
                    "Service not Found");
        }
        if (monService.get().getGroupService() == null) {
            listIdService.add(idService.toString());
            return ticketRepository.nombreTicketInstanceServiceCommun(listIdService);
        }
        List<dz.me.dashboard.entities.Service> listService = serviceRepository
                .findByGroupService(monService.get().getGroupService());
        for (int i = 0; i < listService.size(); i++) {
            listIdService.add(listService.get(i).getId().toString());

        }
        return ticketRepository.nombreTicketInstanceServiceCommun(listIdService);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public Ticket dernierTicketAppeleService(UUID idService) {
        Integer dernierTicket = ticketRepository.dernierTicketAppeleService(idService.toString());
        if (dernierTicket == 0) {
            throw new ResourceNotFoundException(
                    "file attente vide");
        }
        Ticket ticket = ticketRepository.findByTicketAndServiceAndSysDate(idService.toString(),
                dernierTicket.toString());
        ticket.setTicket(ticket.getTicket() + ticket.getService().getServiceLettre());
        return ticket;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public int delaisAttenteMoyen(UUID idService) {
        try {
            return ticketRepository.delaisAttenteMoyen(idService.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public int delaisTraitementMoyen(UUID idService) {
        try {
            return ticketRepository.delaisTraitementMoyen(idService.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public Ticket dernierTicketAppeleServiceCommun(UUID serviceId) {
        // List<ServiceCommun> listServiceCommun =
        // serviceCommunService.serviceCommunByIdService(serviceId);
        List<String> listIdService = new ArrayList<>();
        Optional<dz.me.dashboard.entities.Service> monService = serviceRepository.findById(serviceId);

        if (!monService.isPresent()) {
            throw new ResourceNotFoundException(
                    "Service not Found");
        }

        if (monService.get().getGroupService() == null) {
            listIdService.add(serviceId.toString());
            Ticket ticketc = ticketRepository.dernierTicketAppeleSeviceCommun(listIdService);
            Ticket ticket2 = new Ticket();
            ticket2.setTicket(ticketc.getTicket() + "" + ticketc.getService().getServiceLettre());
            ticket2.setDateAppel(ticketc.getDateAppel());
            ticket2.setDateEdition(ticketc.getDateEdition());
            ticket2.setDateTraitement(ticketc.getDateTraitement());
            ticket2.setId(ticketc.getId());
            ticket2.setIdClient(ticketc.getIdClient());
            ticket2.setIdPseudoTicket(ticketc.getIdPseudoTicket());
            ticket2.setPseudoFromService(ticketc.getPseudoFromService());
            ticket2.setService(ticketc.getService());
            ticket2.setTransferred(ticketc.isTransferred());
            ticket2.setUserAppel(ticketc.getUserAppel());
            ticket2.setUserTrait(ticketc.getUserTrait());
            ticket2.setUserTransfer(ticketc.getUserTransfer());
            ticket2.setGuichet(ticketc.getGuichet());
            ticket2.setPseudoTicket(ticketc.getPseudoTicket());

            return ticket2;
        }
        List<dz.me.dashboard.entities.Service> listService = serviceRepository
                .findByGroupService(monService.get().getGroupService());

        for (int i = 0; i < listService.size(); i++) {

            listIdService.add(listService.get(i).getId().toString());
        }
        Ticket ticketc = ticketRepository.dernierTicketAppeleSeviceCommun(listIdService);
        Ticket ticket2 = new Ticket();
        ticket2.setTicket(ticketc.getTicket() + "" + ticketc.getService().getServiceLettre());
        ticket2.setDateAppel(ticketc.getDateAppel());
        ticket2.setDateEdition(ticketc.getDateEdition());
        ticket2.setDateTraitement(ticketc.getDateTraitement());
        ticket2.setId(ticketc.getId());
        ticket2.setIdClient(ticketc.getIdClient());
        ticket2.setIdPseudoTicket(ticketc.getIdPseudoTicket());
        ticket2.setPseudoFromService(ticketc.getPseudoFromService());
        ticket2.setService(ticketc.getService());
        ticket2.setTransferred(ticketc.isTransferred());
        ticket2.setUserAppel(ticketc.getUserAppel());
        ticket2.setUserTrait(ticketc.getUserTrait());
        ticket2.setUserTransfer(ticketc.getUserTransfer());
        ticket2.setGuichet(ticketc.getGuichet());
        ticket2.setPseudoTicket(ticketc.getPseudoTicket());

        ;
        // System.out.println(ticketc.getService().getServiceLettre().substring(0, 1));
        //
        return ticket2;

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public List<Ticket> historiqueTicketServiceCommun(UUID serviceId, int limit) {
        List<String> listIdService = new ArrayList<>();

        // List<ServiceCommun> listServiceCommun =
        // serviceCommunService.serviceCommunByIdService(serviceId);
        Optional<dz.me.dashboard.entities.Service> monService = serviceRepository.findById(serviceId);
        if (!monService.isPresent()) {
            throw new ResourceNotFoundException(
                    "Service not Found");
        }
        if (monService.get().getGroupService() == null) {
            listIdService.add(serviceId.toString());
            return ticketRepository.dernierTicketAppeleSeviceCommun(listIdService, limit);
        }
        List<dz.me.dashboard.entities.Service> listService = serviceRepository
                .findByGroupService(monService.get().getGroupService());
        for (int i = 0; i < listService.size(); i++) {
            listIdService.add(listService.get(i).getId().toString());
        }
        if (listService.size() == 0) {
            listIdService.add(serviceId.toString());
        }
        List<Ticket> listTickets = ticketRepository.dernierTicketAppeleSeviceCommun(listIdService, limit);
        for (int i = 0; i < listTickets.size(); i++) {
            listTickets.get(i)
                    .setTicket(listTickets.get(i).getTicket() + listTickets.get(i).getService().getServiceLettre());
        }
        /*
         * System.out.println(listTickets.size());
         * List<Ticket> listTickets2 = new ArrayList<Ticket>();
         * for (int i = 0; i < listTickets.size(); i++) {
         * System.out.println(i);
         * Ticket ticket2 = new Ticket();
         * ticket2.setTicket(
         * listTickets.get(i).getTicket() + "" +
         * listTickets.get(i).getService().getServiceLettre());
         * ticket2.setDateAppel(listTickets.get(i).getDateAppel());
         * ticket2.setDateEdition(listTickets.get(i).getDateEdition());
         * ticket2.setDateTraitement(listTickets.get(i).getDateTraitement());
         * ticket2.setId(listTickets.get(i).getId());
         * ticket2.setIdClient(listTickets.get(i).getIdClient());
         * ticket2.setIdPseudoTicket(listTickets.get(i).getIdPseudoTicket());
         * ticket2.setPseudoFromService(listTickets.get(i).getPseudoFromService());
         * ticket2.setService(listTickets.get(i).getService());
         * ticket2.setTransferred(listTickets.get(i).isTransferred());
         * ticket2.setUserAppel(listTickets.get(i).getUserAppel());
         * ticket2.setUserTrait(listTickets.get(i).getUserTrait());
         * ticket2.setUserTransfer(listTickets.get(i).getUserTransfer());
         * ticket2.setGuichet(listTickets.get(i).getGuichet());
         * listTickets2.add(ticket2);}
         */
        return listTickets;

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET') or hasRole('ROLE_GUICHET') or hasRole('ROLE_AFFICHAGE' )")
    public String lastServiceByGuichetUserIdService(UUID idGuichet, UUID IdUserAppel, UUID IdService) {
        return ticketRepository.lastServiceByGuichetUserIdService(idGuichet.toString(), IdUserAppel.toString(),
                IdService.toString());
    }

    @Override
    public int nbrTicketByUser(UUID IdUserTrait) {

        return ticketRepository.nbrTicketByUser(IdUserTrait.toString());

    }

    @Override
    public void deleteOld() {
        ticketRepository.deleteOld();

    }

    @Override
    public Optional<Ticket> lastTicketByGuichet(String idGuichet) {
        return ticketRepository.lastTicketByGuichet(idGuichet);
    }

}
