package coffee.gamble.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import coffee.gamble.service.GambleService;
import coffee.gambler.domain.Gambler;


@Controller
@RequestMapping("gamble")
public class GambleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GambleService gambleService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("")
	public String index(){
		return "gamble/gamble";
	}
	
	@RequestMapping("getGambleEntry")
	public @ResponseBody String getGambleEntry() throws JsonGenerationException, JsonMappingException, IOException{
		return om.writeValueAsString(gambleService.getGambleEntry());
	}
	
	@RequestMapping("getGambleResult")
	public @ResponseBody String getGambleResult() throws JsonGenerationException, JsonMappingException, IOException{
		Gambler loser = gambleService.getGambleResult(gambleService.getGambleEntry());
		return om.writeValueAsString(loser);
	}
	
}
