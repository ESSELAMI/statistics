package dz.me.dashboard.restcontroller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.entities.RubriqueValue;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.models.RubriqueModel;
import dz.me.dashboard.models.RubriqueValueModel;
import dz.me.dashboard.models.ServiceModel;
import dz.me.dashboard.services.RubriqueService;
import dz.me.dashboard.services.RubriqueValueService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/api/v1/rubrique-values")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class RubriqueValueRestController {

    @Autowired
    RubriqueValueService rubriqueValueService;
    @Autowired
    RubriqueService rubriqueService;

    @GetMapping(path = "/by-rubriqueId/{rubrique-id}")
    public ResponseEntity<?> findById(@PathVariable(name = "rubrique-id") String rubriqueId) {

        System.out.println(UUID.fromString(rubriqueId));
        Optional<RubriqueValue> rubriqueValue = rubriqueValueService.findById(UUID.fromString(rubriqueId));

        if (rubriqueValue.isPresent()) {

            return ResponseEntity.ok(rubriqueValue);
        } else {

            return ResponseEntityUtils.ExceptionResponseEntity("Rubrique not Found", HttpStatus.NOT_FOUND.value());

        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(rubriqueValueService.findAll());
    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() RubriqueValueModel rubriqueValueModel) {
        try {

            Optional<Rubrique> rubrique = rubriqueService
                    .findById(UUID.fromString(rubriqueValueModel.getRubriqueId()));
            RubriqueValue rubriqueValue = new RubriqueValue();
            try {

                rubriqueValue.setId(UUID.fromString(rubriqueValueModel.getId()));
                rubriqueValue.setInsertionDate(rubriqueValueModel.getInsertionDate());
                rubriqueValue.setValue(rubriqueValueModel.getValue());

                // rubriqueValue= rubriqueValueService.save(rubriqueValue);
            } catch (Exception e) {

            }

            rubriqueValue = modelMapper.map(rubriqueValueModel, RubriqueValue.class);
            rubriqueValue.setRubrique(rubrique.get());

            return ResponseEntity.ok(rubriqueValueService.save(rubriqueValue));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody() RubriqueValueModel rubriqueValueModel) {
        try {
            Optional<Rubrique> rubrique = rubriqueService.findById(UUID.fromString(rubriqueValueModel.getRubriqueId()));
            RubriqueValue rubriqueValue = new RubriqueValue();

            rubriqueValue.setInsertionDate(rubriqueValueModel.getInsertionDate());
            rubriqueValue.setId(UUID.fromString(rubriqueValueModel.getId()));
            rubriqueValue.setRubrique(rubrique.get());

            rubriqueValue.setValue(rubriqueValueModel.getValue());

            return ResponseEntity.ok(rubriqueValueService.save(rubriqueValue));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/delete/{rubriqueValueId}")
    public ResponseEntity<?> delete(HttpServletRequest request,
            @PathVariable(name = "rubriqueValueId") String rubriqueValueId) {
        try {
            rubriqueValueService.deleteById(UUID.fromString(rubriqueValueId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }
}
