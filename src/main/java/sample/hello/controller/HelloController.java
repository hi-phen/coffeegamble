package sample.hello.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.hello.service.HelloService;

@Controller
public class HelloController {
	Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	@Autowired
	HelloService helloService;
	
	@Autowired
	ObjectMapper om;
	
//	@RequestMapping("/")
	public String Hello(Model model){
		logger.debug("Hello");
		
		model.addAttribute("hello", helloService.hello());
		return "hello/Hello";
	}
	
//	@RequestMapping("/json")
	public @ResponseBody String Hello() throws JsonGenerationException, JsonMappingException, IOException{
		logger.debug("Hello Json");
		return om.writeValueAsString(helloService.hello());
	}
}
