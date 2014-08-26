package coffee.gamble.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coffee.gamble.domain.GambleLoser;
import coffee.gamble.service.GambleService;
import coffee.gambler.domain.Gambler;
import coffee.statistics.service.StatisticsService;


@Controller
@RequestMapping("gamble")
public class GambleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GambleService gambleService;
	
	@Autowired
	StatisticsService statisticsService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("")
	public String index(){
		return "gamble/gamble";
	}
	
	@RequestMapping("loser")
	public String loser(){
		return "loser/loser";
	}
	
	@RequestMapping("statistics")
	public String Statistics(){
		return "Statistics/Statistics";
	}
	
	@RequestMapping("getGambleEntry")
	public @ResponseBody String getGambleEntry(@RequestParam(defaultValue="1") float chanceMultipleValue) throws JsonGenerationException, JsonMappingException, IOException{
		return om.writeValueAsString(gambleService.getGambleEntry(chanceMultipleValue));
	}
	
	@RequestMapping("getGambleResult")
	public @ResponseBody String getGambleResult(@RequestParam(defaultValue="1") float chanceMultipleValue) throws JsonGenerationException, JsonMappingException, IOException{
		Gambler loser = gambleService.getGambleResult(gambleService.getGambleEntry(chanceMultipleValue));
		return om.writeValueAsString(loser);
	}
	
	@RequestMapping("addLoser")
	public @ResponseBody String addLoser(long gamblerKey,String gamblerName,String dateStr) throws ParseException, JsonGenerationException, JsonMappingException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gambler gambler = new Gambler(gamblerKey);
		gambler.setName(gamblerName);
		return om.writeValueAsString(gambleService.addLoser(gambler,sdf.parse(dateStr)).getId());
	}
	
	@RequestMapping("getLoser")
	public @ResponseBody String getLoser(@RequestParam(defaultValue="0")int offset) throws JsonGenerationException, JsonMappingException, IOException{
		return om.writeValueAsString(gambleService.getLoser(offset));
	}
	
	@RequestMapping("deleteLoser")
	public @ResponseBody String deleteLoser(long loserKey) throws JsonGenerationException, JsonMappingException, IOException{
		gambleService.deleteLoser(new GambleLoser(loserKey));
		return om.writeValueAsString(Boolean.TRUE);
		
	}
	
	@RequestMapping("deleteLoserAll")
	public @ResponseBody String deleteLoserAll() throws JsonGenerationException, JsonMappingException, IOException{
		gambleService.deleteLoserAll();
		return om.writeValueAsString(Boolean.TRUE);
	}
	
	@RequestMapping("getStatistics")
	public @ResponseBody String getStatistics(@RequestParam int year, @RequestParam int month) throws JsonGenerationException, JsonMappingException, IOException, ParseException{
		return om.writeValueAsString(statisticsService.getStatistics(year, month));
	}
	
}
