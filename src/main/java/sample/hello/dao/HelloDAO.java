package sample.hello.dao;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

@Repository
public class HelloDAO {
	DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	public String hello(String value){
		return "";
	}
}
