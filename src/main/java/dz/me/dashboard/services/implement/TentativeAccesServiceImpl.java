package dz.me.dashboard.services.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dz.me.dashboard.entities.TentativeAcces;
import dz.me.dashboard.exceptions.ResourceForbiddenException;
import dz.me.dashboard.repositories.TentativeAccesRepository;
import dz.me.dashboard.services.TentativeAccesService;
import dz.me.dashboard.services.UtilsParamService;
import dz.me.dashboard.utils.UtilsIP;

/**
 *
 * @author Tarek Mekriche
 */
@Service
// @Transactional
public class TentativeAccesServiceImpl implements TentativeAccesService {

	@Autowired
	TentativeAccesRepository tentativeAccesRepository;

	@Autowired
	private UtilsParamService utilsParamService;

	@Override
	public int getNombreTentative(String ip) {
		try {
			return tentativeAccesRepository.getNombreTentative(ip);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public TentativeAcces addTentative(TentativeAcces tentativeAcces) {
		return tentativeAccesRepository.save(tentativeAcces);

	}

	@Override
	public List<Map<String, Object>> searchDelaisLastTentative(String ip) {
		try {

			List<Map<String, Object>> list = tentativeAccesRepository.searchDelaisLastTentative(ip);
			if (list.size() == 0)
				throw new RuntimeException("Nouvelle tentative");
			return list;
		} catch (Exception e) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", 259200000);
			map.put("tentative", 259200000);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(map);
			return list;
		}
	}

	@Override
	public Date sysdate() {
		return tentativeAccesRepository.sysdate();
	}

	@Override
	public void validerAcces(HttpServletRequest request) {
		String ip = UtilsIP.getClientIpAddr(request);
		int delaisBlocage = utilsParamService.getDelaisBlocage();// 30 seconds
		int delais = utilsParamService.getDelaisAttenteTentativeLoginBySeconds();
		int tentativeMax = utilsParamService.getNombreTentativeLogin();

		// verifier l'acces avec l'@ip simultané

		// int tentative = getNombreTentative(ip);
		// int delaisLastTentative = searchDelaisLastTentative(ip);
		List<Map<String, Object>> list = searchDelaisLastTentative(ip);
		// traitement d'acces simultané dans le login

		try {
			if (Integer.parseInt(list.get(0).get("tentative").toString()) >= tentativeMax
					&& Integer.parseInt(list.get(0).get("total").toString()) <= delaisBlocage) {
				int delaisMinutes = (delaisBlocage + 10) / 60;
				throw new ResourceForbiddenException("Vous avez dépassé le nombre de tentatives(" + tentativeMax
						+ "), réessayer plus tard (environ " + delaisMinutes + " minutes(s)) ...");
			}

			// modifier tentative a 0 si le delais est dépassé
			System.out.println("list " + list);
			if (Integer.parseInt(list.get(0).get("total").toString()) > delaisBlocage
					|| (Integer.parseInt(list.get(0).get("tentative").toString()) < tentativeMax
							&& Integer.parseInt(list.get(0).get("total").toString()) > delais)) {
				int tentative = 0;
				TentativeAcces tentativeAcces = new TentativeAcces(tentative, sysdate(), ip);
				addTentative(tentativeAcces);
			}

		} catch (Exception e) {
			throw new ResourceForbiddenException(e.getMessage());
		}

	}

}
