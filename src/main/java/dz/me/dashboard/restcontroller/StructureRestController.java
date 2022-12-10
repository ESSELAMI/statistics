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

import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Wilaya;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.models.StructureModel;
import dz.me.dashboard.services.ServiceService;
import dz.me.dashboard.services.StructureService;
import dz.me.dashboard.services.WilayaSerivce;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/structures")
@SecurityRequirement(name = "bearerAuth")
public class StructureRestController {
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private WilayaSerivce wilayaService;
    @Autowired
    private StructureService structureService;
    @Autowired
    private WilayaSerivce wilayaSerivce;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/by-token")
    public ResponseEntity<?> findByToken(HttpServletRequest request) {
        String serviceId = jwtUtils.ClaimAsString(request, "service-id");
        try {
            Structure structure = serviceService.findById(UUID.fromString(serviceId)).get().getGroupService()
                    .getStructure();
            return ResponseEntity.ok(structure);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/strucutre/{strucutre-id}")
    public ResponseEntity<?> findByToken(@PathVariable(name = "strucutre-id") String structureId) {
        Optional<Structure> strucutre = structureService.findById(UUID.fromString(structureId));
        if (strucutre.isPresent()) {
            return ResponseEntity.ok(strucutre.get());
        } else {
            return ResponseEntityUtils.ExceptionResponseEntity("strucutre not Found", HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/all/wilaya/{wilaya-id}")
    public ResponseEntity<?> findByIdStructure(@PathVariable(name = "wilaya-id") String wilayaId) {
        Optional<Wilaya> wilaya = wilayaSerivce.findById((UUID.fromString(wilayaId)));
        if (!wilaya.isPresent()) {

            return ResponseEntityUtils.ExceptionResponseEntity("wilaya-id not Found", HttpStatus.NOT_FOUND.value());
        }
        return ResponseEntity.ok(structureService.findByWilaya(wilaya.get()));

    }

    @GetMapping("/all/wilaya/by-token")
    public ResponseEntity<?> findByStructureByToken(HttpServletRequest request) {
        try {
            String serviceId = jwtUtils.ClaimAsString(request, "service-id");
            Optional<Service> service = serviceService.findById(UUID.fromString(serviceId));
            Wilaya wilaya = structureService.findById(service.get().getGroupService().getStructure().getId()).get()
                    .getWilaya();

            return ResponseEntity.ok(structureService.findByWilaya(wilaya));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @PostMapping("/ajouter-structure")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() StructureModel structureModel) {
        try {
            Optional<Wilaya> wilaya = wilayaSerivce.findById(UUID.fromString(structureModel.getIdWilaya()));
            if (!wilaya.isPresent()) {
                return ResponseEntityUtils.ExceptionResponseEntity("wilaya-id not found", HttpStatus.NOT_FOUND.value());
            }
            Structure structure = new Structure();
            structure.setStructure(structureModel.getName());
            structure.setWilaya(wilaya.get());
            structure.setCodeStructure(structureModel.getCodeStructure());
            structure.setStructureAr(structureModel.getStructureAr());
            structure.setAdresse(structureModel.getAdresse());
            structure.setAdresseAr(structureModel.getAdresseAr());
            try {
                structure.setId(UUID.fromString(structureModel.getId()));
            } catch (Exception e) {
            }

            return ResponseEntity.ok(structureService.save(structure));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PostMapping("/ajouter-list-tructure")
    public ResponseEntity<?> saveAll(HttpServletRequest request, @RequestBody() List<StructureModel> structureModel) {
        try {

            List<Structure> structures = new ArrayList<Structure>();

            for (int i = 1; i < structureModel.size(); i++) {
                System.out.println(structureModel.get(i).getAdresseAr());
                Structure structure = new Structure();
                structure.setStructure(structureModel.get(i).getName());
                structure.setWilaya(
                        wilayaService.findByCodeWilaya(structureModel.get(i).getIdWilaya()).get());
                structure.setCodeStructure(structureModel.get(i).getCodeStructure());
                structure.setStructureAr(structureModel.get(i).getStructureAr());
                structure.setAdresse(structureModel.get(i).getAdresse());
                structure.setAdresseAr(structureModel.get(i).getAdresseAr());
                try {
                    structureService.save(structure);
                } catch (Exception e) {
                    System.out.println("----------------" + structure + "----------------");
                }

                // structures.add(structure);
            }

            return ResponseEntity.ok(structureService.saveAll(structures));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/supprimer-strucutre/{strucutre-id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
            @PathVariable(name = "strucutre-id") String structureId) {
        try {
            structureService.deleteById(UUID.fromString(structureId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
