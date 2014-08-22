package coffee.gambler.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import coffee.gambler.domain.Gambler;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:war/WEB-INF/classes/config/spring/root-context.xml"
		,"file:war/WEB-INF/classes/config/spring/applicationContext.xml"
		,"file:war/WEB-INF/classes/config/spring/appServlet/servlet-context.xml"
})
public class GamblerServiceTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	GamblerService gamblerService;
	
	 private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setUp() {
	    helper.setUp();
	}
	
	@After
	public void tearDown() {
	    helper.tearDown();
	}
    
	@Test
	public void addGambler() {
		Gambler gambler = new Gambler("김민섭", true);
		Key key = gamblerService.addGambler(gambler);
		Gambler addGambler = gamblerService.getGambler(key);
		assertEquals(gambler.getName(), addGambler.getName());
	}
	
	@Test
	public void getGamblerList(){
		Gambler gambler = new Gambler("김민섭", true);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("신재용", false);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("김종성", true);
		gamblerService.addGambler(gambler);
		
		List<Gambler> gamblerList = gamblerService.getGamblerList();
		assertEquals(3, gamblerList.size());
	}
	
	@Test
	public void getActiveGamblerList(){
		Gambler gambler = new Gambler("김민섭", true);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("신재용", false);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("김종성", true);
		gamblerService.addGambler(gambler);
		
		List<Gambler> gamblerList = gamblerService.getActiveGamblerList(true);
		assertEquals(2, gamblerList.size());
	}
	
	@Test
	public void updateGambler(){
		Gambler gambler = new Gambler("김민섭", true);
		Key key1 = gamblerService.addGambler(gambler);
		gambler = gamblerService.getGambler(key1);
		gambler.setActive(false);
		Key key2 = gamblerService.updateGambler(gambler);
		gambler = gamblerService.getGambler(key2);
		assertEquals(false, gambler.isActive());
		List<Gambler> gamblerList = gamblerService.getGamblerList();
		assertEquals(1, gamblerList.size());		
	}
	
	@Test
	public void deleteGambler(){
		Gambler gambler = new Gambler("김민섭", true);
		Key key1 = gamblerService.addGambler(gambler);
		Gambler gambler2 = new Gambler();
		gambler2.setGamblerId(KeyFactory.createKey("gambler", key1.getId()));
		gamblerService.deleteGambler(gambler2);
		
		assertEquals(0, gamblerService.getGamblerList().size());
	}
	
}
