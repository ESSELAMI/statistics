package dz.me.dashboard.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Tarek Mekriche
 */
@Data
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private int guichet;

}
