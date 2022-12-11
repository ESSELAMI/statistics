package dz.me.dashboard.restcontroller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.models.RubriqueModel;
import dz.me.dashboard.models.ServiceModel;
import dz.me.dashboard.services.RubriqueService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/api/v1/rubriques")
@SecurityRequirement(name = "bearerAuth")
public class RubriqueRestController {

    @Autowired
    RubriqueService rubriqueService;
    @Autowired
    ServiceService serviceService;

    @GetMapping(path = "/{rubrique-id}")
    public ResponseEntity<?> findById(@PathVariable(name = "rubrique-id") String rubriqueId) {

        System.out.println(UUID.fromString(rubriqueId));
        Optional<Rubrique> rubrique = rubriqueService.findById(UUID.fromString(rubriqueId));

        if (rubrique.isPresent()) {

            return ResponseEntity.ok(rubrique);
        } else {

            return ResponseEntityUtils.ExceptionResponseEntity("Rubrique not Found", HttpStatus.NOT_FOUND.value());

        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(rubriqueService.findAll());
    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/ajouter-rubrique")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() RubriqueModel rubriqueModel) {
        try {

            Optional<Service> service = serviceService
                    .findById(UUID.fromString(rubriqueModel.getServiceId()));
            Rubrique rubrique = new Rubrique();
            try {

                rubrique.setId(UUID.fromString(rubriqueModel.getId()));
                rubrique.setNameAr(rubriqueModel.getNameAr().toString());
                rubrique.setNameFr(rubriqueModel.getNameFr().toString());
                rubrique.setNameEn(rubriqueModel.getNameEn().toString());
                rubriqueService.save(rubrique);
            } catch (Exception e) {

            }

            rubrique = modelMapper.map(rubriqueModel, Rubrique.class);
            rubrique.setService(service.get());

            return ResponseEntity.ok(rubriqueService.save(rubrique));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
