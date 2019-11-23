package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.AddressDTO;
import com.algaworks.algafood.domain.model.Address;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var addressToAdressTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);
		
		addressToAdressTypeMap.<String>addMapping(
				addressOrigin -> addressOrigin.getCity().getState().getName(), 
			   (addressDestiny, value) -> addressDestiny.getCity().setState(value));
		
		return modelMapper;
	}

}
