package com.algaworks.algafood.api.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.openapi.RootEntryPointControllerOpenApiV2;

@RestController
@RequestMapping(path = "/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControllerV2 implements RootEntryPointControllerOpenApiV2 {
	
	@Autowired
	private AlgaLinksV2 algaLinks;
	
	@GetMapping
	public RootEntryPointDTOV2 root() {
		var rootEntyPointDto = new RootEntryPointDTOV2();
		
		rootEntyPointDto.add(algaLinks.linkToKitchens("kitchens"));
		rootEntyPointDto.add(algaLinks.linkToCities("cities"));
		
		return rootEntyPointDto;
	}
	
	public static class RootEntryPointDTOV2 extends RepresentationModel<RootEntryPointDTOV2> {
		
	}

}
