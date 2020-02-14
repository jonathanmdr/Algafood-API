package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.input.OrderItemInput;
import com.algaworks.algafood.api.v2.model.input.CityInputV2;
import com.algaworks.algafood.api.v1.model.AddressDTO;
import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.OrderItem;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var addressToAdressTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);
		
		addressToAdressTypeMap.<String>addMapping(
				addressOrigin -> addressOrigin.getCity().getState().getName(), 
			   (addressDestiny, value) -> addressDestiny.getCity().setState(value));
		
		modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
			.addMappings(mapper -> mapper.skip(OrderItem::setId));
		
		modelMapper.createTypeMap(CityInputV2.class, City.class)
		    .addMappings(mapper -> mapper.skip(City::setId));
		
		return modelMapper;
	}

}
