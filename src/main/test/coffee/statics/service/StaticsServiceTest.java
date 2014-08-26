package coffee.statics.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import coffee.gamble.service.GambleService;
import coffee.gambler.domain.Gambler;
import coffee.gambler.service.GamblerService;
import coffee.statics.service.StaticsService;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:war/WEB-INF/classes/config/spring/root-context.xml"
		,"file:war/WEB-INF/classes/config/spring/applicationContext.xml"
		,"file:war/WEB-INF/classes/config/spring/appServlet/servlet-context.xml"
})
public class StaticsServiceTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StaticsService staticsService;
	
	@Autowired
	GambleService gambleService;
	@Autowired
	GamblerService gamblerService;

	Key g;
	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	@Before
	public void setUp() {
	    helper.setUp();
	    Gambler gambler = new Gambler("김민섭", true);
		gamblerService.addGambler(gambler);
		
		
		gambler = new Gambler("신재용", true);
		g = gamblerService.addGambler(gambler);
		gambleService.addLoser(gamblerService.getGambler(g));
		
		gambler = new Gambler("김종성", true);
		g = gamblerService.addGambler(gambler);
		gambleService.addLoser(gamblerService.getGambler(g));
		gambleService.addLoser(gamblerService.getGambler(g));
		gambleService.addLoser(gamblerService.getGambler(g));
		
		
		gambler = new Gambler("유혜은", true);
		g = gamblerService.addGambler(gambler);
		gambleService.addLoser(gamblerService.getGambler(g));
		gambleService.addLoser(gamblerService.getGambler(g));
		
		gambler = new Gambler("방소영", true);
		g = gamblerService.addGambler(gambler);
		
		gambler = new Gambler("변혜란", true);
		g = gamblerService.addGambler(gambler);
	}
	
	@After
	public void tearDown() {
	    helper.tearDown();
	}
	
	@Test
	public void getStatics() throws ParseException{
		Map statics = staticsService.getStatics(2014,8);
		assertNotNull(statics.get("monthly"));
		assertNotNull(statics.get("weekly"));
		assertNotNull(statics.get("weeklyText"));
		assertNotNull(statics.get("gamblerMap"));
	}
}
