package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.KitchenController;
import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.input.KitchenInput;
import com.algaworks.algafood.domain.model.Kitchen;

@Component
public class KitchenMapper extends RepresentationModelAssemblerSupport<Kitchen, KitchenDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public KitchenMapper() {
		super(KitchenController.class, KitchenDTO.class);
	}

	@Override
	public KitchenDTO toModel(Kitchen kitchen) {
		KitchenDTO kitchenDto = createModelWithId(kitchen.getId(), kitchen);
		modelMapper.map(kitchen, kitchenDto);

		kitchenDto.add(algaLinks.linkToKitchens("kitchens"));

		return kitchenDto;
	}

	public Kitchen toDomainObject(KitchenInput kitchenInput) {
		return modelMapper.map(kitchenInput, Kitchen.class);
	}

	public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
		modelMapper.map(kitchenInput, kitchen);
	}

}
