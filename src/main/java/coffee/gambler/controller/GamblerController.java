package coffee.gambler.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coffee.gambler.domain.Gambler;
import coffee.gambler.service.GamblerService;

import com.google.appengine.api.datastore.KeyFactory;

@Controller
@RequestMapping("gambler")
public class GamblerController {

	@Autowired
	GamblerService gamblerService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("")
	public String index(){
		return "gambler/gambler";
	}
	
	@RequestMapping("getGamblerList")
	public @ResponseBody String getGamblerList() throws JsonGenerationException, JsonMappingException, IOException{
		return om.writeValueAsString(gamblerService.getGamblerList());
	}
	
	@RequestMapping("addGambler")
	public @ResponseBody String addGambler(Gambler gambler) throws JsonGenerationException, JsonMappingException, IOException{
		gamblerService.addGambler(gambler);
		return om.writeValueAsString(gamblerService.getGamblerList());
	}
	
	@RequestMapping("deleteGambler")
	public @ResponseBody String deleteGambler(@RequestParam String key,Gambler gambler) throws JsonGenerationException, JsonMappingException, IOException{
		gambler.setGamblerId(KeyFactory.createKey("gambler", Long.valueOf(key)));
		gamblerService.deleteGambler(gambler);
		return om.writeValueAsString(gamblerService.getGamblerList());
	}
	
	@RequestMapping("updateGambler")
	public @ResponseBody String updateGambler(@RequestParam String key,Gambler gambler) throws JsonGenerationException, JsonMappingException, IOException{
		gambler.setGamblerId(KeyFactory.createKey("gambler", Long.valueOf(key)));
		gamblerService.updateGambler(gambler);
		return om.writeValueAsString(gamblerService.getGamblerList());
	}
	
}
