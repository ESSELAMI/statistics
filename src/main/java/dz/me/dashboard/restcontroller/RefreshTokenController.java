package dz.me.dashboard.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.me.dashboard.models.RefreshTokenRequest;
import dz.me.dashboard.services.RefreshTokenService;
import dz.me.dashboard.utils.ResponseEntityUtils;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@RestController
@RequestMapping("api/v1/auth/refresh-token")
@CrossOrigin
public class RefreshTokenController {

	@Autowired
	private RefreshTokenService refreshTokenService;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createNewAccessToken(@RequestBody RefreshTokenRequest refreshTokenId) {
		try {
			return ResponseEntity.ok(refreshTokenService.createAccessToken(refreshTokenId));

		} catch (Exception exp) {
			return ResponseEntityUtils.ExceptionResponseEntity(exp.getMessage(), HttpStatus.FORBIDDEN.value());
		}
	}

}
