package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.openapi.controller.RootEntryPointControllerOpenApi;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController implements RootEntryPointControllerOpenApi {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public RootEntryPointDTO root() {
		var rootEntyPointDto = new RootEntryPointDTO();
		
		rootEntyPointDto.add(algaLinks.linkToKitchens("kitchens"));
		rootEntyPointDto.add(algaLinks.linkToOrders("orders"));
		rootEntyPointDto.add(algaLinks.linkToRestaurants("restaurants"));
		rootEntyPointDto.add(algaLinks.linkToGroups("groups"));
		rootEntyPointDto.add(algaLinks.linkToUsers("users"));
		rootEntyPointDto.add(algaLinks.linkToPermissions("permissions"));
		rootEntyPointDto.add(algaLinks.linkToPaymentForms("payment-forms"));
		rootEntyPointDto.add(algaLinks.linkToCities("cities"));
		rootEntyPointDto.add(algaLinks.linkToStates("states"));
		rootEntyPointDto.add(algaLinks.linkToStatistics("statistics"));
		
		return rootEntyPointDto;
	}
	
	public static class RootEntryPointDTO extends RepresentationModel<RootEntryPointDTO> {
		
	}

}
