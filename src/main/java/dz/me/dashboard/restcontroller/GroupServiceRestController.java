package dz.me.dashboard.restcontroller;

import java.util.ArrayList;
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

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.models.GroupServiceModel;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.services.GroupServiceService;
import dz.me.dashboard.services.StructureService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@RequestMapping(path = "/api/v1/group-services")
@SecurityRequirement(name = "bearerAuth")
public class GroupServiceRestController {

    @Autowired
    private GroupServiceService groupServiceService;

    @Autowired
    private StructureService structureService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupServiceService.findById(UUID.fromString(id)));
    }

    @PostMapping("/ajouter-group-service")
    public ResponseEntity<?> save(HttpServletRequest request, @RequestBody() GroupServiceModel groupService) {
        try {
            Structure structure = structureService.findById(UUID.fromString(groupService.getStructureId())).get();
            GroupService groupS = new GroupService();
            groupS.setGroupName(groupService.getGroupName());

            groupS.setStructure(structure);
            try {
                groupS.setId(UUID.fromString(groupService.getId()));
            } catch (Exception e) {

            }
            return ResponseEntity.ok(groupServiceService.save(groupS));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @PostMapping("/ajouter-list-group-service")
    public ResponseEntity<?> saveAll(HttpServletRequest request, @RequestBody() List<GroupServiceModel> groupServices) {
        try {
            System.out.println("cc");
            List<GroupService> groupS = new ArrayList<GroupService>();
            for (int i = 1; i < groupServices.size(); i++) {
                System.out.println(i);
                Structure structure = structureService
                        .findById(UUID.fromString(groupServices.get(i).getStructureId()))
                        .get();
                GroupService group = new GroupService();
                group.setGroupName(groupServices.get(i).getGroupName());
                group.setStructure(structure);
                groupS.add(group);

            }
            return ResponseEntity.ok(groupServiceService.saveAll(groupS));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @DeleteMapping("/supprimer-group-service/{group-service-id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
            @PathVariable(name = "group-service-id") String groupServiceId) {
        try {
            groupServiceService.delete(UUID.fromString(groupServiceId));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

    @GetMapping("/structure/{structure-id}")
    public ResponseEntity<?> findByIdStructure(@PathVariable(name = "structure-id") String structureId) {
        try {
            return ResponseEntity.ok(groupServiceService.findByIdStructure(UUID.fromString(structureId)));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
