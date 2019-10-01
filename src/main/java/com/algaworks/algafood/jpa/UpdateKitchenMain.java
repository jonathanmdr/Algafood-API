package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

public class UpdateKitchenMain {
	
	public static void main(String ... args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		KitchenRepository kitchenRepository = applicationContext.getBean(KitchenRepository.class);
		
		Kitchen kitchen1 = new Kitchen();
		kitchen1.setId(1L);
		kitchen1.setName("Brasileira");
		
		kitchen1 = kitchenRepository.save(kitchen1);
		
		System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
	}

}
