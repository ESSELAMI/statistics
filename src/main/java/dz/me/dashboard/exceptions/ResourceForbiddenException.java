package dz.me.dashboard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author MEKRICHE TAREK
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceForbiddenException(String message) {
		super(message);
	}
}