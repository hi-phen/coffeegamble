package coffee.gamble.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
	

	DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	public Key addLoser(Entity gambler) {
		return datastoreService.put(gambler);
	}

	public Iterable<Entity> getLoser(Date start, Date end) {
		Query query = new Query("gambleloser");
		FilterPredicate greater = new Query.FilterPredicate("loseDate",FilterOperator.GREATER_THAN_OR_EQUAL,start);
		FilterPredicate less = new Query.FilterPredicate("loseDate",FilterOperator.LESS_THAN_OR_EQUAL,end);
		
		query.setFilter(CompositeFilterOperator.and(greater,less)).addSort("loseDate",SortDirection.ASCENDING);
		return datastoreService.prepare(query).asIterable();
	}

}
