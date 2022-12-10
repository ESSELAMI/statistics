package dz.me.dashboard.restcontroller;

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

import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.services.TraitementService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/traitements")
@SecurityRequirement(name = "bearerAuth")
public class TraitementController {

    @Autowired
    private TraitementService traitementService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/by-token")
    public ResponseEntity<?> findTraitementByToken(HttpServletRequest request) {
        String serviceId = jwtUtils.ClaimAsString(request, "service-id");
        return ResponseEntity.ok(traitementService.findTraitementByService(UUID.fromString(serviceId)));
    }

    @GetMapping("/service/{service-id}")
    public ResponseEntity<?> findTraitementIdService(@PathVariable(name = "service-id") String serviceId) {
        return ResponseEntity.ok(traitementService.findTraitementByService(UUID.fromString(serviceId)));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findTraitementIdService() {
        return ResponseEntity.ok(traitementService.findAll());
    }

    @PostMapping("/ajouter-traitement")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() Traitement traitement) {
        try {
            traitement.setId(null);
            return ResponseEntity.ok(traitementService.save(traitement));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/supprimer-traitement/{traitement-id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
            @PathVariable(name = "traitement-id") String traitementId) {
        try {
            traitementService.deleteById(UUID.fromString(traitementId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }
}
