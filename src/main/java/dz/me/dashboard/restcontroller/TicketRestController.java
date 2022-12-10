package dz.me.dashboard.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Ticket;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.DelaisModel;
import dz.me.dashboard.models.GuichetModelDisplay;
import dz.me.dashboard.models.MessageResponse;
import dz.me.dashboard.models.TicketModel;
import dz.me.dashboard.services.GuichetService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.services.TicketService;
import dz.me.dashboard.services.WSService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/tickets")
@SecurityRequirement(name = "bearerAuth")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private GuichetService guichetService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private WSService wSservice;

    @GetMapping("/imprimer-ticket-suivant/service/{service-id}/prioritaire/{prioritaire}")
    public ResponseEntity<?> imprimerTicketSuivant(HttpServletRequest request,
            @PathVariable(name = "service-id") String serviceId, @PathVariable(name = "prioritaire") int prioritaire) {
        try {

            Ticket t = ticketService.imprimerTicketSuivant(UUID.fromString(serviceId), prioritaire);
            int countTicketInstance = ticketService.nombreTicketInstancesService(UUID.fromString(serviceId));
            wSservice.notifyCountTicketInstance(countTicketInstance);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/appeler-ticket-suivant/tickets/{last-ticket-traiter-id}/traitement/{traitement-comma-separated}")
    public ResponseEntity<?> appelTicketSuivant1(HttpServletRequest request,
            @PathVariable("last-ticket-traiter-id") String idLastTicket,
            @PathVariable("traitement-comma-separated") String traitement) {
        try {

            String userId = jwtUtils.ClaimAsString(request, "user-id");
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            String guichetId = jwtUtils.ClaimAsString(request, "guichet-id");
            Ticket t = ticketService.appelTicketSuivant(UUID.fromString(serviceId), idLastTicket,
                    UUID.fromString(userId),
                    UUID.fromString(guichetId),
                    traitement);
            wSservice.notifyAppelTicket(t);
            return ResponseEntity
                    .ok(t);
        } catch (Exception e) {
            HttpHeaders h = new HttpHeaders();
            h.add("Exception", e.getMessage());
            return new ResponseEntity<Object>(
                    e.getMessage(), h, HttpStatus.FORBIDDEN);
            // return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(),
            // HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/traite-ticket-encours/tickets/{last-ticket-traiter-id}/traitement/{traitement-comma-separated}")
    public ResponseEntity<?> finTraiteTicketenCours(HttpServletRequest request,
            @PathVariable("last-ticket-traiter-id") String idLastTicket,
            @PathVariable("traitement-comma-separated") String traitement) {
        try {

            String userId = jwtUtils.ClaimAsString(request, "user-id");
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            String guichetId = jwtUtils.ClaimAsString(request, "guichet-id");
            ticketService.finTraitementTicket(UUID.fromString(serviceId), idLastTicket,
                    UUID.fromString(userId),
                    UUID.fromString(guichetId),
                    traitement);
            return ResponseEntity
                    .ok(new MessageResponse("OK"));
        } catch (Exception e) {
            HttpHeaders h = new HttpHeaders();
            h.add("Exception", e.getMessage());
            return new ResponseEntity<Object>(
                    e.getMessage(), h, HttpStatus.FORBIDDEN);
            // return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(),
            // HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/dernier-ticket-appele/service/by-token")
    public ResponseEntity<?> dernierTicketAppele(HttpServletRequest request) {

        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity.ok(ticketService.dernierTicketAppeleService(UUID.fromString(serviceId)));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/dernier-ticket-non-traite/by-token")
    public ResponseEntity<?> lastServiceByGuichetUserIdService(HttpServletRequest request) {

        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");

            String guichetId = jwtUtils.ClaimAsString(request, "guichet-id");
            String UserId = jwtUtils.ClaimAsString(request, "user-id");

            return ResponseEntity
                    .ok(new MessageResponse(ticketService.lastServiceByGuichetUserIdService(UUID.fromString(guichetId),
                            UUID.fromString(UserId), UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/dernier-ticket-appele/service/{service-id}")
    public ResponseEntity<?> dernierTicketAppeleByService(HttpServletRequest request,
            @PathVariable(name = "service-id") String serviceId) {

        try {
            return ResponseEntity.ok(ticketService.dernierTicketAppeleService(UUID.fromString(serviceId)));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @GetMapping("/dernier-ticket-appele/service/service-commun")
    public ResponseEntity<?> dernierTicketAppeleServiceCommun(HttpServletRequest request) {
        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            Ticket ticket = ticketService.dernierTicketAppeleServiceCommun(UUID.fromString(serviceId));
            if (ticket == null) {
                ticket = new Ticket();
                ticket.setTicket("0");
                return ResponseEntity.ok(ticket);

            }
            return ResponseEntity.ok(ticketService.dernierTicketAppeleServiceCommun(UUID.fromString(serviceId)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/nombre-tickets-en-instances/service/by-token")
    public ResponseEntity<?> nombreTicketInstancesService(HttpServletRequest request) {
        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity
                    .ok(new TicketModel(ticketService.nombreTicketInstancesService(UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/nombre-tickets-en-instances/service/{service-id}")
    public ResponseEntity<?> nombreTicketInstancesService(HttpServletRequest request,
            @PathVariable(name = "service-id") String serviceId) {
        try {
            return ResponseEntity
                    .ok(new TicketModel(ticketService.nombreTicketInstancesService(UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/nombre-tickets-trait/byTocken")
    public ResponseEntity<?> nbrTicketByUser(HttpServletRequest request) {
        try {
            String userId = jwtUtils.ClaimAsString(request, "user-id");
            Integer i = ticketService.nbrTicketByUser(UUID.fromString(userId));

            return ResponseEntity
                    .ok(new MessageResponse(i.toString()));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/nombre-tickets-en-instances/service/service-commun")
    public ResponseEntity<?> nombreTicketInstancesServiceCommun(HttpServletRequest request) {
        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity
                    .ok(new TicketModel(ticketService.nombreTicketInstancesServiceCommun(UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/transferer-ticket-to-service/service/{id-service}/ticket/{num-ticket}")
    public ResponseEntity<?> tansfererTicket(HttpServletRequest request,
            @PathVariable(name = "id-service") String serviceId, @PathVariable(name = "num-ticket") String ticket) {
        try {

            String userId = jwtUtils.ClaimAsString(request, "user-id");
            String monSerivceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity
                    .ok(ticketService.tansfererTicket(UUID.fromString(userId), UUID.fromString(serviceId), ticket,
                            UUID.fromString(monSerivceId)));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/delais-attente-moyen-minutes/service/by-token")
    public ResponseEntity<?> delaisAttenteMoyen(HttpServletRequest request) {
        try {

            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity.ok(new DelaisModel(ticketService.delaisAttenteMoyen(UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/delais-traitement-moyen-minute/service/by-token")
    public ResponseEntity<?> delaisTraitementMoyen(HttpServletRequest request) {
        try {

            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity.ok(new DelaisModel(ticketService.delaisTraitementMoyen(UUID.fromString(serviceId))));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/historique-ticket-service-commun/limit-ticket-historique/{limit}")
    public ResponseEntity<?> historiqueTicketServiceCommun(HttpServletRequest request, @PathVariable int limit) {
        try {

            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            return ResponseEntity.ok(ticketService.historiqueTicketServiceCommun(UUID.fromString(serviceId), limit));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/last-ticket-called-commun/byGuichet/byToken")
    public ResponseEntity<?> lastTicketByGuichet(HttpServletRequest request) {
        try {

            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
            // Optional<Structure> structure =
            // structureService.findById(service.get().getStructure().getId());
            List<Service> services = serviceService.findByGroupService(service.get().getGroupService());
            List<GuichetModelDisplay> guichetModelDisplays = new ArrayList<GuichetModelDisplay>();
            for (int i = 0; i < services.size(); i++) {
                List<Guichet> guichets = new ArrayList<Guichet>();
                guichets = guichetService.findAllByService(services.get(i));
                GuichetModelDisplay guichetModelDisplay = new GuichetModelDisplay();
                for (int j = 0; j < guichets.size(); j++) {
                    Optional<Ticket> ticket = ticketService.lastTicketByGuichet(guichets.get(j).getId().toString());
                    try {
                        System.out.println(guichets.get(j).getId());
                        guichetModelDisplay = new GuichetModelDisplay();
                        guichetModelDisplay.setGuichet(guichets.get(j).getGuichet());
                        if (ticket.isPresent()) {
                            if (ticket.get().getDateTraitement() != null) {
                                guichetModelDisplay.setTicket("");

                            } else {
                                guichetModelDisplay.setTicket(ticket.get().getTicket());
                            }
                            guichetModelDisplays.add(guichetModelDisplay);
                        } else {
                            guichetModelDisplay.setTicket("");
                            guichetModelDisplays.add(guichetModelDisplay);

                        }
                    } catch (Exception e) {
                        guichetModelDisplay.setTicket(ticket.get().getTicket());
                        guichetModelDisplays.add(guichetModelDisplay);
                        System.out.println("Exception " + e.getMessage() + guichets.get(j).getName());
                    }
                }
            }

            return ResponseEntity.ok(guichetModelDisplays);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }

    }

}
