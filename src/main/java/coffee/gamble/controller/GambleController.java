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

import coffee.gambler.service.GamblerService;


@Controller
public class GambleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GamblerService gamblerService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("/")
	public String index(){
		return "coffeegamble/gamble";
	}
	
	@RequestMapping("gambler/getGamblerList")
	public @ResponseBody String getGamblerList() throws JsonGenerationException, JsonMappingException, IOException{
		return om.writeValueAsString(gamblerService.getGamblerList());
	}
}
