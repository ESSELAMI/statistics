package dz.me.dashboard.restcontroller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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

import dz.me.dashboard.entities.Role;
import dz.me.dashboard.models.OperationModel;
import dz.me.dashboard.models.RoleModel;
import dz.me.dashboard.services.RoleService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@RestController
@RequestMapping(path = "/api/v1/roles")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class RoleRestController {
    @Autowired
    RoleService roleService;

    @GetMapping("/{role-id}")
    public ResponseEntity<?> findById(@PathVariable(name = "role-id") String idRole) {
        Optional<Role> role = roleService.findById(UUID.fromString(idRole));
        if (!role.isPresent()) {
            return ResponseEntityUtils.ExceptionResponseEntity(idRole + " role not found",
                    HttpStatus.FORBIDDEN.value());
        }
        return ResponseEntity.ok(role.get());
    }

    @DeleteMapping("/delete/{role-id}")
    public ResponseEntity<?> delete(@PathVariable(name = "role-id") String idRole) {
        try {
            roleService.deleteById(UUID.fromString(idRole));
            OperationModel operationModel = new OperationModel("success");
            return ResponseEntity.ok(operationModel);
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(),
                    HttpStatus.FORBIDDEN.value());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody RoleModel roleModel) {
        Role role = new Role();
        role.setName(roleModel.getName());

        return ResponseEntity.ok(roleService.save(role));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(roleService.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody() RoleModel roleModel) {
        try {

            Role role = new Role();
            role.setId(UUID.fromString(roleModel.getId()));
            role.setName(roleModel.getName());

            return ResponseEntity.ok(roleService.save(role));
        } catch (Exception e) {
            return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN.value());
        }
    }

}
