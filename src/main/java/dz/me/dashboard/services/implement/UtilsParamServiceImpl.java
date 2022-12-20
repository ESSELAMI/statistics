package dz.me.dashboard.services.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dz.me.dashboard.services.UtilsParamService;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Service
public class UtilsParamServiceImpl implements UtilsParamService {

	// delais apres les tentatives d'acces login en secondes
	@Value("${app.securite.delais.tentative.seconds}")
	private Integer delaisAttenteTentativeLoginBySeconds;

	// nombre max de tentatives login simultanés
	@Value("${app.securite.nombre_tentative}")
	private Integer nombreTentativeLogin;

	@Value("${app.securite.tentative.delais.blocage.seconds}")
	private Integer delaisBlocage;

	@Override
	public Integer getDelaisAttenteTentativeLoginBySeconds() {
		try {
			return delaisAttenteTentativeLoginBySeconds;

		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer getNombreTentativeLogin() {
		try {
			return nombreTentativeLogin;

		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer getDelaisBlocage() {
		try {
			return delaisBlocage;

		} catch (Exception e) {
			return 0;
		}
	}

}
