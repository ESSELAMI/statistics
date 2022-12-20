package dz.me.dashboard.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Data
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
