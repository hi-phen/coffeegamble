package coffee.gambler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.gambler.dao.GamblerDAO;
import coffee.gambler.domain.Gambler;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
* <pre>
* coffee.gambler.service
*   |_ GamblerService.java
* </pre>
* 
* Desc :  게임 참가자 서비스
* @Author  : Min seob Kim
* @Version : 
 */
@Service
public class GamblerService {

	@Autowired
	GamblerDAO gamblerDAO;
	
	/**
	 * Desc : 참가자 추가 
	 * @Method Name : addGambler
	 * @param gambler
	 * @return
	 */
	public Key addGambler(Gambler gambler) {
		return gamblerDAO.addGambler(gambler.toEntity());
	}

	/**
	 * Desc : 참가자 조회 (단일) 
	 * @Method Name : getGambler
	 * @param key
	 * @return
	 */
	public Gambler getGambler(Key key) {
		try {
			Entity gambler = gamblerDAO.getGambler(key);
			return new Gambler(gambler);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	/**
	 * Desc : 참가자 조회 (전체) 
	 * @Method Name : getGamblerList
	 * @return
	 */
	public List<Gambler> getGamblerList() {
		Iterable<Entity> gamblers = gamblerDAO.getGamblerlist();
		ArrayList<Gambler> gamblerList = new ArrayList<Gambler>();
		
		for(Entity gambler: gamblers){
			gamblerList.add(new Gambler(gambler));
		}
		return gamblerList;
	}
	
	/**
	 * Desc : 비활성/활성 화된 참가자 조회 
	 * @Method Name : getActiveGamblerList
	 * @return
	 */
	public List<Gambler> getActiveGamblerList(boolean isActive) {
		Iterable<Entity> gamblers = gamblerDAO.getActiveGamblerlist(isActive);
		ArrayList<Gambler> gamblerList = new ArrayList<Gambler>();
		
		for(Entity gambler: gamblers){
			gamblerList.add(new Gambler(gambler));
		}
		return gamblerList;
	}

	public Key updateGambler(Gambler gambler) {
		return gamblerDAO.updateGambler(gambler.toEntity());
	}

	public void deleteGambler(Gambler gambler) {
		gamblerDAO.deleteGambler(gambler.toEntity());
	}

	public void activeGambler(long keyId,boolean active) {
		Key key = KeyFactory.createKey("gambler", keyId);
		Gambler gambler = getGambler(key);
		gambler.setActive(active);
		gamblerDAO.updateGambler(gambler.toEntity());
	}

}
