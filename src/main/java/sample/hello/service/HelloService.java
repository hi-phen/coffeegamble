package sample.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.hello.dao.HelloDAO;
import sample.hello.domain.Hello;

@Service
public class HelloService {
	
	@Autowired
	HelloDAO helloDAO;
	
	public String hello(){
		return helloDAO.hello("Hello :D");
	}
}
