package com.algaworks.algafood.api.v2.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.KitchenControllerV2;
import com.algaworks.algafood.api.v2.model.KitchenDTOV2;
import com.algaworks.algafood.api.v2.model.input.KitchenInputV2;
import com.algaworks.algafood.domain.model.Kitchen;

@Component
public class KitchenMapperV2 extends RepresentationModelAssemblerSupport<Kitchen, KitchenDTOV2> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinksV2 algaLinks;

	public KitchenMapperV2() {
		super(KitchenControllerV2.class, KitchenDTOV2.class);
	}

	@Override
	public KitchenDTOV2 toModel(Kitchen kitchen) {
		KitchenDTOV2 kitchenDto = createModelWithId(kitchen.getId(), kitchen);
		modelMapper.map(kitchen, kitchenDto);

		kitchenDto.add(algaLinks.linkToKitchens("kitchens"));

		return kitchenDto;
	}

	public Kitchen toDomainObject(KitchenInputV2 kitchenInput) {
		return modelMapper.map(kitchenInput, Kitchen.class);
	}

	public void copyToDomainObject(KitchenInputV2 kitchenInput, Kitchen kitchen) {
		modelMapper.map(kitchenInput, kitchen);
	}

}
