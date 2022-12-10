package dz.me.dashboard.restcontroller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.models.ServiceModel;
import dz.me.dashboard.models.ServiceModelUpdate;
import dz.me.dashboard.services.GroupServiceService;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.services.StructureService;
import dz.me.dashboard.services.TraitementService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/services")
@SecurityRequirement(name = "bearerAuth")
public class ServiceRestController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private StructureService structureService;
    @Autowired
    private GroupServiceService groupServiceService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TraitementService traitementService;

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

    @GetMapping("/{service-id}/ajouter-traitement/{traitement-id}")
    public ResponseEntity<?> ajouterTraitementToService(@PathVariable(name = "service-id") String serviceId,
            @PathVariable(name = "traitement-id") String traitementId) {
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        Optional<Traitement> traitement = traitementService.findById(UUID.fromString(traitementId));
        if (!traitement.isPresent()) {
            return ResponseEntityUtils.ExceptionResponseEntity("traitementId not Found", HttpStatus.NOT_FOUND.value());
        }

        if (service.isPresent()) {
            service.get().getTraitements().add(traitement.get());
            serviceService.save(service.get());
            return ResponseEntity.ok(service.get());

        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("Service not Found", HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/{service-id}/supprimer-traitement/{traitement-id}")
    public ResponseEntity<?> deleteTraitementToService(@PathVariable(name = "service-id") String serviceId,
            @PathVariable(name = "traitement-id") String traitementId) {
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        Optional<Traitement> traitement = traitementService.findById(UUID.fromString(traitementId));
        if (!traitement.isPresent()) {
            return ResponseEntityUtils.ExceptionResponseEntity("traitementId not Found", HttpStatus.NOT_FOUND.value());
        }
        if (service.isPresent()) {
            service.get().getTraitements().remove(traitement.get());
            serviceService.save(service.get());

            return ResponseEntity.ok(service.get());

        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("Service not Found", HttpStatus.NOT_FOUND.value());
        }
    }
    /*
     * @GetMapping("/all/meme-structure/{structure-id}")
     * public ResponseEntity<?> findByIdStructure(@PathVariable(name =
     * "structure-id") String structureId) {
     * 
     * Optional<Structure> structure =
     * structureService.findById(UUID.fromString(structureId));
     * if (structure.isPresent()) {
     * return ResponseEntity.ok(serviceService.findByStructure(structure.get()));
     * } else {
     * return ResponseEntityUtils.ExceptionResponseEntity("structure-id not Found",
     * HttpStatus.NOT_FOUND.value());
     * }
     * }
     */
    /*
     * @GetMapping("/all/meme-structure/by-token")
     * public ResponseEntity<?> findByStructureByToken(HttpServletRequest request) {
     * String serviceId = jwtUtils.ClaimAsString(request, "service-id");
     * Optional<Service> service =
     * serviceService.findById(UUID.fromString(serviceId));
     * Optional<Structure> structure = structureService
     * .findById(service.get().getGroupService().getStructure().getId());
     * if (structure.isPresent()) {
     * return ResponseEntity.ok(serviceService.findByStructure(structure.get()));
     * } else {
     * return ResponseEntityUtils.ExceptionResponseEntity("structure-id not Found",
     * HttpStatus.NOT_FOUND.value());
     * }
     * }
     */

    @GetMapping("/all/meme-group/by-token")
    public ResponseEntity<?> findByGroupByToken(HttpServletRequest request) {
        String serviceId = jwtUtils.ClaimAsString(request, "service-id");
        Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
        // Optional<Structure> structure =
        // structureService.findById(service.get().getStructure().getId());

        return ResponseEntity.ok(serviceService.findByGroupService(service.get().getGroupService()));

    }

    @GetMapping(value = "/all/group-service/{group-id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> findByGroupId(HttpServletRequest request,
            @PathVariable(name = "group-id") String groupId) {

        return ResponseEntity
                .ok(serviceService.findByGroupService(groupServiceService.findById(UUID.fromString(groupId)).get()));

    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/ajouter-service")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() ServiceModel serviceModel) {
        try {

            Optional<GroupService> groupService = groupServiceService
                    .findById(UUID.fromString(serviceModel.getGroupServiceId()));
            if (!groupService.isPresent()) {
                return ResponseEntityUtils.ExceptionResponseEntity("groupService not found",
                        HttpStatus.NOT_FOUND.value());
            }
            Service service = new Service();
            try {
                service.setId(UUID.fromString(serviceModel.getId()));
            } catch (Exception e) {

            }
            // service.setService(serviceModel.getService());
            // service.setServiceLettre(serviceModel.getServiceLettre());
            // service.setServiceAr(serviceModel.getServiceAr());
            service = modelMapper.map(serviceModel, Service.class);
            service.setGroupService(groupService.get());

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
                Optional<GroupService> groupService = groupServiceService
                        .findById(UUID.fromString(serviceModel.get(i).getGroupServiceId()));
                service.setGroupService(groupService.get());

                serviceService.save(service);
            }

            return ResponseEntity.ok(serviceModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PutMapping("/modifier-service")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody() ServiceModelUpdate serviceModel) {
        try {
            Optional<Structure> structure = structureService.findById(UUID.fromString(serviceModel.getStructureId()));
            if (!structure.isPresent()) {
                return ResponseEntityUtils.ExceptionResponseEntity("Structure not found", HttpStatus.NOT_FOUND.value());
            }

            Optional<GroupService> groupService = groupServiceService
                    .findById(UUID.fromString(serviceModel.getGroupId()));
            if (!groupService.isPresent()) {
                return ResponseEntityUtils.ExceptionResponseEntity("groupService not found",
                        HttpStatus.NOT_FOUND.value());
            }
            Service service = new Service();
            service.setId(UUID.fromString(serviceModel.getId()));
            service.setService(serviceModel.getName());
            service.setGroupService(groupService.get());
            return ResponseEntity.ok(serviceService.save(service));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/supprimer-service/{service-id}")
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
