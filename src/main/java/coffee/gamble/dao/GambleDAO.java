package coffee.gamble.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
* <pre>
* coffee.gamble.dao
*   |_ GambleDAO.java
* </pre>
* 
* Desc : 내기 DAO 
* @Author  : Min seob Kim
* @Version :
 */
@Repository
public class GambleDAO {
	

	@Autowired
	DatastoreService datastoreService;
	
	private final int FETCH_LIMIT = 10;
	
	public Key addLoser(Entity gambler) {
		return datastoreService.put(gambler);
	}
	
	/**
	 * Desc : 당첨자 조회 (기간별) 
	 * @Method Name : getLoser
	 * @param start
	 * @param end
	 * @return
	 */
	public Iterable<Entity> getLoser(Date start, Date end) {
		Query query = new Query("gambleloser");
		FilterPredicate greater = new Query.FilterPredicate("loseDate",FilterOperator.GREATER_THAN_OR_EQUAL,start);
		FilterPredicate less = new Query.FilterPredicate("loseDate",FilterOperator.LESS_THAN_OR_EQUAL,end);
		
		query.setFilter(CompositeFilterOperator.and(greater,less)).addSort("loseDate",SortDirection.ASCENDING);
		return datastoreService.prepare(query).asIterable();
	}

	/**
	 * Desc : 당첨자 조회 (limit) 
	 * @Method Name : getLoser
	 * @param offset
	 * @return
	 */
	public Iterable<Entity> getLoser(int offset) {
		Query query = new Query("gambleloser").addSort("loseDate",SortDirection.DESCENDING);
		return datastoreService.prepare(query).asIterable(FetchOptions.Builder.withOffset(offset).limit(FETCH_LIMIT));
	}
	
	/**
	 * Desc : 모든 당첨자 조회 
	 * @Method Name : getAllLoser
	 * @return
	 */
	public Iterable<Entity> getAllLoser() {
		Query query = new Query("gambleloser").addSort("loseDate",SortDirection.DESCENDING);
		return datastoreService.prepare(query).asIterable();
	}
	
	/**
	 * Desc : 당첨자 삭제 
	 * @Method Name : deleteLoser
	 * @param loser
	 */
	public void deleteLoser(Entity loser) {
		datastoreService.delete(loser.getKey());
	}

	/**
	 * Desc : 모든 당첨자 삭제 
	 * @Method Name : deleteLoserAll
	 */
	public void deleteLoserAll() {
		Iterable<Entity> loserList = getAllLoser();
		ArrayList<Key> loserKeys = new ArrayList<Key>();
		for(Entity loser : loserList){
			loserKeys.add(loser.getKey());
		}
		datastoreService.delete(loserKeys);
	}

}
