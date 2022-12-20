package dz.me.dashboard.restcontroller;

import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Service;

import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.models.ServiceModel;
import dz.me.dashboard.models.ServiceModelUpdate;

import dz.me.dashboard.services.ServiceService;

import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@RestController
@RequestMapping(path = "/api/v1/services")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class ServiceRestController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/by-token")
    public ResponseEntity<?> findByToken(HttpServletRequest request) {
        String serviceId = jwtUtils.ClaimAsString(request, "service-id");
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        if (service.isPresent()) {
            return ResponseEntity.ok(service.get());
        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("Service not Found", HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/{service-id}")
    public ResponseEntity<?> findByToken(@PathVariable(name = "service-id") String serviceId) {
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        if (service.isPresent()) {
            return ResponseEntity.ok(service.get());
        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("Service not Found", HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(serviceService.findAll());
    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() ServiceModel serviceModel) {
        try {
            Service service = new Service();
            try {
                service.setId(UUID.fromString(serviceModel.getId()));
            } catch (Exception e) {

            }
            // service.setService(serviceModel.getService());
            // service.setServiceLettre(serviceModel.getServiceLettre());
            // service.setServiceAr(serviceModel.getServiceAr());
            service = modelMapper.map(serviceModel, Service.class);

            return ResponseEntity.ok(serviceService.save(service));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PostMapping("/ajouter-list-service")
    public ResponseEntity<?> saveAll(HttpServletRequest request, @RequestBody() List<ServiceModel> serviceModel) {
        try {

            for (int i = 1; i <= serviceModel.size(); i++) {

                Service service = new Service();
                service.setService(serviceModel.get(i).getService());
                service.setServiceAr(serviceModel.get(i).getServiceAr());
                service.setServiceLettre(serviceModel.get(i).getServiceLettre());

                serviceService.save(service);
            }

            return ResponseEntity.ok(serviceModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody() ServiceModelUpdate serviceModel) {
        try {

            Service service = new Service();
            service.setId(UUID.fromString(serviceModel.getId()));
            service.setService(serviceModel.getName());

            return ResponseEntity.ok(serviceService.save(service));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/delete/{service-id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable(name = "service-id") String serviceId) {
        try {
            serviceService.deleteById(UUID.fromString(serviceId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
