package coffee.gambler.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
* <pre>
* coffee.gambler.dao
*   |_ GamblerDAO.java
* </pre>
* 
* Desc : 참가자 DAO 
* @Author  : Min seob Kim
* @Version :
 */
@Repository
public class GamblerDAO {

	@Autowired
	DatastoreService datastoreService;
	
	/**
	 * Desc : 참가자 추가
	 * @Method Name : addGambler
	 * @param gambler
	 * @return
	 */
	public Key addGambler(Entity gambler) {
		return datastoreService.put(gambler);
	}

	/**
	 * Desc : 참가자 조회 (단일) 
	 * @Method Name : getGambler
	 * @param key
	 * @return
	 * @throws EntityNotFoundException
	 */
	public Entity getGambler(Key key) throws EntityNotFoundException {
		return datastoreService.get(key);
	}
	
	/**
	 * Desc : 활성화(비활성화)된 참가자 조회 
	 * @Method Name : getActiveGamblerlist
	 * @param isActive
	 * @return
	 */
	public Iterable<Entity> getActiveGamblerlist(boolean isActive) {
		FilterPredicate isActiveFilter = new Query.FilterPredicate("active", FilterOperator.EQUAL, isActive);
		Query query = new Query("gambler").setFilter(isActiveFilter).addSort("addDate",SortDirection.DESCENDING);
		
		return datastoreService.prepare(query).asIterable();
	}
	
	/**
	 * Desc : 참가자 조회 (전체)
	 * @Method Name : getGamblerlist
	 * @return
	 */
	public Iterable<Entity> getGamblerlist() {
		Query query = new Query("gambler").addSort("addDate",SortDirection.DESCENDING);
		return datastoreService.prepare(query).asIterable();
	}

	/**
	 * Desc : 참가자 수정 
	 * @Method Name : updateGambler
	 * @param entity
	 * @return
	 */
	public Key updateGambler(Entity entity) {
		return datastoreService.put(entity);
	}

	/**
	 * Desc : 참가자 삭제 (단일)
	 * @Method Name : deleteGambler
	 * @param entity
	 */
	public void deleteGambler(Entity entity) {
		datastoreService.delete(entity.getKey());
	}

	/**
	 * Desc : 참가자 삭제 (전체)
	 * @Method Name : deleteGamblerAll
	 */
	public void deleteGamblerAll() {
		Iterable<Entity> gamblerlist = getGamblerlist();
		ArrayList<Key> gamblerKeys = new ArrayList<Key>();
		for(Entity gambler : gamblerlist){
			gamblerKeys.add(gambler.getKey());
		}
		datastoreService.delete(gamblerKeys);
	}

}
