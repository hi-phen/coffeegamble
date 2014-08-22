package coffee.gamble.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import coffee.gambler.service.GamblerService;


@Controller
@RequestMapping("gamble")
public class GambleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GamblerService gamblerService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("")
	public String index(){
		return "gamble/gamble";
	}
	
	
}
