package coffee.gamble.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import coffee.gamble.domain.GambleLoser;
import coffee.gambler.domain.Gambler;
import coffee.gambler.service.GamblerService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:war/WEB-INF/classes/config/spring/root-context.xml"
		,"file:war/WEB-INF/classes/config/spring/applicationContext.xml"
		,"file:war/WEB-INF/classes/config/spring/appServlet/servlet-context.xml"
})
public class GambleServiceTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Autowired
	GambleService gambleService;
	@Autowired
	GamblerService gamblerService;

	Key g1;
	Key g2;
	
	@Before
	public void setUp() {
		helper.setUp();
		Gambler gambler = new Gambler("김민섭", true);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("신재용", true);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("김종성", true);
		g1 = gamblerService.addGambler(gambler);
		gambler = new Gambler("유혜은", true);
		g2 = gamblerService.addGambler(gambler);
		gambler = new Gambler("방소영", true);
		gamblerService.addGambler(gambler);
		gambler = new Gambler("변혜란", true);
		gamblerService.addGambler(gambler);
	}
	
	@After
	public void tearDown() {
	    helper.tearDown();
	}
	
	@Test
	public void getGambleEntry() {
		List<Gambler> gambleEntry = gambleService.getGambleEntry();
		float chanceSum = 0;
//		for(Gambler gambler : gambleEntry){
//			logger.debug("****************************************");
//			logger.debug("NAME : " + gambler.getName());
//			for(String msg : gambler.getWeightLog()){
//				logger.debug(msg);
//			}
//			chanceSum += gambler.getChance();
//			logger.debug("CHANGE : " + gambler.getChance());
//			logger.debug("****************************************");
//		}
//		logger.debug("chanceSum : " + chanceSum);
	}
	
	@Test
	public void addLoser() throws Exception{
		List<Gambler> activeGamblerList = gamblerService.getActiveGamblerList(true);
		Gambler gambler = activeGamblerList.get(1);
		Key addLoser = gambleService.addLoser(gambler);
		assertNotNull(addLoser);
	}
	
	@Test
	public void getLoserOnThisWeek() throws Exception{
		List<GambleLoser> loserOnThisWeek = gambleService.getLoserOnThisWeek();
		assertEquals(g1, loserOnThisWeek.get(0).getGamblerId());
		assertEquals(g2, loserOnThisWeek.get(1).getGamblerId());
	}
	
	@Test
	public void getGambleResult() throws Exception{
		for(int j=0;j<10;j++){
			HashMap<String, Integer> bucket = new HashMap<String, Integer>();
			for(int i=0;i<7;i++){
				Gambler gambleResult = gambleService.getGambleResult(gambleService.getGambleEntry());
				bucket.put(gambleResult.getName(), (bucket.get(gambleResult.getName())==null?0:bucket.get(gambleResult.getName()))+1);
			}
			logger.debug(bucket.toString());
		}
	}
	
	@Test
	public void deleteLoser() throws Exception{
		Gambler gambler = gamblerService.getGambler(g1);
		Key loserkey = gambleService.addLoser(gambler);
		assertEquals(1, gambleService.getLoser().size());
		gambleService.deleteLoser(new GambleLoser(loserkey.getId()));
		assertEquals(0, gambleService.getLoser().size());
	}
	
	
}
