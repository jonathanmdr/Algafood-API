package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.input.KitchenInput;
import com.algaworks.algafood.domain.model.Kitchen;

@Component
public class KitchenMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public KitchenDTO toDto(Kitchen kitchen) {
		return modelMapper.map(kitchen, KitchenDTO.class);
	}
	
	public List<KitchenDTO> toCollectionDto(List<Kitchen> kitchens) {
		return kitchens.stream().map(kitchen -> toDto(kitchen)).collect(Collectors.toList());
	}
	
	public Kitchen toDomainObject(KitchenInput kitchenInput) {
		return modelMapper.map(kitchenInput, Kitchen.class);
	}
	
	public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {		
		modelMapper.map(kitchenInput, kitchen);
	}

}
