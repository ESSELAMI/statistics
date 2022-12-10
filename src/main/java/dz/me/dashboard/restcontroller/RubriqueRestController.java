package dz.me.dashboard.restcontroller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.entities.Rubrique;
import dz.me.dashboard.services.RubriqueService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/api/v1/rubriques")
@SecurityRequirement(name = "bearerAuth")
public class RubriqueRestController {

    @Autowired
    RubriqueService rubriqueService;

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

}
