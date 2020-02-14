package com.algaworks.algafood.api.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.controller.CityControllerV2;
import com.algaworks.algafood.api.v2.controller.KitchenControllerV2;

@Component
public class AlgaLinksV2 {

	public Link linkToCities(String linkRelation) {
		return linkTo(CityControllerV2.class).withRel(linkRelation);
	}

	public Link linkToCities() {
		return linkToCities(IanaLinkRelations.SELF.value());
	}

	public Link linkToKitchens(String linkRelation) {
		return linkTo(KitchenControllerV2.class).withRel(linkRelation);
	}

	public Link linkToKitchens() {
		return linkToKitchens(IanaLinkRelations.SELF.value());
	}

}
