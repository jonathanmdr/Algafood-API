package com.algaworks.algafood.api.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Links")
@Getter
@Setter
public class LinksModelOpenApi {
	
	private LinkModel linkRelation;
	
	@ApiModel("Link")
	@Getter
	@Setter
	private class LinkModel {
		
		private String href;
		private boolean templated;
		
	}

}
