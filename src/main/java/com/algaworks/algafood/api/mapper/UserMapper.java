package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.api.model.input.UserInput;
import com.algaworks.algafood.api.model.input.UserSummaryInput;
import com.algaworks.algafood.domain.model.User;

@Component
public class UserMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserSummaryDTO toSummaryDto(User user) {
		return modelMapper.map(user, UserSummaryDTO.class);
	}
	
	public List<UserSummaryDTO> toCollectionSummaryDto(List<User> users) {
		return users.stream().map(user -> toSummaryDto(user)).collect(Collectors.toList());
	}
	
	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}
	
	public void copyToDomainObject(UserSummaryInput userSummaryInput, User user) {
		modelMapper.map(userSummaryInput, user);
	}

}
