package dz.me.dashboard.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dz.me.dashboard.entities.TentativeAcces;

public interface TentativeAccesService {

	public int getNombreTentative(String ip);

	public TentativeAcces addTentative(TentativeAcces tentativeAcces);

	public List<Map<String, Object>> searchDelaisLastTentative(String ip);

	public void validerAcces(HttpServletRequest request);

	public Date sysdate();
}
