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
		return om.writeValueAsString(gamblerService.addGambler(gambler).getId());
	}
	
	@RequestMapping("deleteGambler")
	public @ResponseBody String deleteGambler(@RequestParam long key) throws JsonGenerationException, JsonMappingException, IOException{
		gamblerService.deleteGambler(new Gambler(key));
		return om.writeValueAsString(Boolean.TRUE);
	}
	
	@RequestMapping("deleteGamblerAll")
	public @ResponseBody String deleteGambler() throws JsonGenerationException, JsonMappingException, IOException{
		gamblerService.deleteGamblerAll();
		return om.writeValueAsString(Boolean.TRUE);
	}
	
	@RequestMapping("activeGambler")
	public @ResponseBody String activeGambler(@RequestParam long key,@RequestParam boolean active) throws JsonGenerationException, JsonMappingException, IOException{
		gamblerService.activeGambler(key,active);
		return om.writeValueAsString(Boolean.TRUE);
	}
	
}
