package dz.me.dashboard.models;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenModel {

    private String access_token;

}
