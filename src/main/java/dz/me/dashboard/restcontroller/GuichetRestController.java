package dz.me.dashboard.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.User;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.GuichetModel;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.services.GuichetService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.services.UserService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */

@RestController
@RequestMapping(path = "/api/v1/guichets")
@SecurityRequirement(name = "bearerAuth")
public class GuichetRestController {

    @Autowired
    private GuichetService guichetService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/by-token")
    public ResponseEntity<?> findByToken(HttpServletRequest request) {
        String guichetId = jwtUtils.ClaimAsString(request, "guichet-id");
        Optional<Guichet> guichet = guichetService.findGuichetById(UUID.fromString(guichetId));

        if (guichet.isPresent()) {
            return ResponseEntity.ok(guichet.get());
        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("Guichet not Found", HttpStatus.NOT_FOUND.value());

        }

    }

    @GetMapping("/{guichet-id}")
    public ResponseEntity<?> findById(@PathVariable(name = "guichet-id") UUID guichetId) {
        Optional<Guichet> guichet = guichetService.findGuichetById(guichetId);
        if (guichet.isPresent()) {
            return ResponseEntity.ok(guichet.get());
        } else {
            return ResponseEntityUtils.ExceptionResponseEntity(guichetId + " Guichet not Found",
                    HttpStatus.NOT_FOUND.value());
        }

    }

    @GetMapping("/all/service/{service-id}")
    public ResponseEntity<?> findAllByService(@PathVariable(name = "service-id") String serviceId) {
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        if (service.isPresent()) {

            return ResponseEntity.ok(guichetService.findAllByService(service.get()));

        } else {
            return ResponseEntityUtils.ExceptionResponseEntity(serviceId + " Service not Found",
                    HttpStatus.NOT_FOUND.value());

        }
    }

    @GetMapping("/all/service/by-token")
    public ResponseEntity<?> findAllByToken(HttpServletRequest request) {
        String serviceId = jwtUtils.ClaimAsString(request, "service-id");
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));

        if (service.isPresent()) {

            return ResponseEntity.ok(guichetService.findAllByService(service.get()));

        } else {
            return ResponseEntityUtils.ExceptionResponseEntity(serviceId + " Service not Found",
                    HttpStatus.NOT_FOUND.value());

        }
    }

    @GetMapping("/all/user/{username}")
    public ResponseEntity<?> findAllUsername(@PathVariable(name = "username") String username) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {

            return ResponseEntity.ok(guichetService.findAllByService(user.get().getService()));

        } else {
            return ResponseEntity.ok(new ArrayList<>());

        }
    }

    @PostMapping("/ajouter-guichet")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() GuichetModel guichetModel) {
        try {
            Guichet guichet = new Guichet();
            guichet.setGuichet(guichetModel.getGuichet());
            guichet.setName(guichetModel.getName());
            Optional<Service> service = serviceService.findById(UUID.fromString(guichetModel.getServiceId()));
            if (!service.isPresent()) {
                return ResponseEntityUtils.ExceptionResponseEntity(guichetModel.getServiceId() + " Service not Found",
                        HttpStatus.NOT_FOUND.value());
            }
            guichet.setService(service.get());
            try {
                guichet.setId(UUID.fromString(guichetModel.getId()));
            } catch (Exception e) {
                return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());
            }
            return ResponseEntity.ok(guichetService.save(guichet));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @PostMapping("/ajouter--list-guichet")
    public ResponseEntity<?> saveAll(HttpServletRequest request, @RequestBody() List<GuichetModel> guichetModel) {
        try {

            for (int i = 1; i < guichetModel.size(); i++) {
                Guichet guichet = new Guichet();
                guichet.setGuichet(guichetModel.get(i).getGuichet());
                guichet.setName(guichetModel.get(i).getName());
                Optional<Service> service = serviceService
                        .findById(UUID.fromString(guichetModel.get(i).getServiceId()));
                if (!service.isPresent()) {
                    return ResponseEntityUtils.ExceptionResponseEntity(
                            guichetModel.get(i).getServiceId() + " Service not Found",
                            HttpStatus.NOT_FOUND.value());
                }
                guichet.setService(service.get());
                guichetService.save(guichet);

            }
            return ResponseEntity.ok(guichetModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @DeleteMapping("/supprimer-guichet/{guichet-id}")
    public ResponseEntity<?> delete(@PathVariable(name = "guichet-id") UUID guichetId) {
        try {
            guichetService.deleteByid(guichetId);
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());

        }

    }

}
