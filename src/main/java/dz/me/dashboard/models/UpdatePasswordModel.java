package dz.me.dashboard.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Data
@Getter
@Setter
public class UpdatePasswordModel {

    private String oldPassword;
    private String newPassword;
}
