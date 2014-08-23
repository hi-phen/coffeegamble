package coffee.gambler.dao;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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

	DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	public Key addGambler(Entity gambler) {
		return datastoreService.put(gambler);
	}

	public Entity getGambler(Key key) throws EntityNotFoundException {
		return datastoreService.get(key);
	}
	
	public Iterable<Entity> getActiveGamblerlist(boolean isActive) {
		FilterPredicate isActiveFilter = new Query.FilterPredicate("active", FilterOperator.EQUAL, isActive);
		Query query = new Query("gambler").setFilter(isActiveFilter).addSort("addDate",SortDirection.DESCENDING);
		
		return datastoreService.prepare(query).asIterable();
	}

	public Iterable<Entity> getGamblerlist() {
		Query query = new Query("gambler").addSort("addDate",SortDirection.DESCENDING);
		return datastoreService.prepare(query).asIterable();
	}

	public Key updateGambler(Entity entity) {
		return datastoreService.put(entity);
	}

	public void deleteGambler(Entity entity) {
		datastoreService.delete(entity.getKey());
	}

}
