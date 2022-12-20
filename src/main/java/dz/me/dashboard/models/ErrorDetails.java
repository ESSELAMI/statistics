package dz.me.dashboard.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String error;
	private int status;

}
