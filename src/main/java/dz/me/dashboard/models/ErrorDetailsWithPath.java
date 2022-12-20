package dz.me.dashboard.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetailsWithPath {
	private Date timestamp;
	private String message;
	private String error;
	private String path;
	private int status;

}
