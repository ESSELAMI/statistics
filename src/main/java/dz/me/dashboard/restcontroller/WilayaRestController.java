package dz.me.dashboard.restcontroller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Wilaya;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.services.WilayaSerivce;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/wilayas")
@SecurityRequirement(name = "bearerAuth")
public class WilayaRestController {

    @Autowired
    private WilayaSerivce wilayaSerivce;

    @GetMapping("/all")
    public List<Wilaya> all() {
        return wilayaSerivce.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(wilayaSerivce.findById(id));
    }

    @PostMapping("/ajouter-wilaya")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() Wilaya wilaya) {
        try {
            // wilaya.setId(null);
            System.out.println(wilaya);
            return ResponseEntity.ok(wilayaSerivce.save(wilaya));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PostMapping("/ajouter-list-wilaya")
    public ResponseEntity<?> saveAllList(HttpServletRequest request, @RequestBody() List<Wilaya> wilaya) {
        try {
            // wilaya.setId(null);
            System.out.println(wilaya);
            return ResponseEntity.ok(wilayaSerivce.saveAll(wilaya));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/supprimer-wilaya/{wilaya-id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
            @PathVariable(name = "wilaya-id") String wilayaId) {
        try {
            wilayaSerivce.deleteById(UUID.fromString(wilayaId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
