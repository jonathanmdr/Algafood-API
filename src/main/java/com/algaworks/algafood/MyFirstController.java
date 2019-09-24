package com.algaworks.algafood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.model.Customer;
import com.algaworks.algafood.di.service.CustomerActivationService;

@Controller
public class MyFirstController {
	
	@Autowired
	private CustomerActivationService customerActivationService;
	
	@GetMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		Customer customer = new Customer("Jonathan", "jonathan.mdr@hotmail.com", "44999215470");
		customerActivationService.activate(customer);
		return "Hello";
	}

}
