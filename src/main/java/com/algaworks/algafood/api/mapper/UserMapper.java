package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.api.model.input.UserInput;
import com.algaworks.algafood.api.model.input.UserSummaryInput;
import com.algaworks.algafood.domain.model.User;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<User, UserSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public UserMapper() {
		super(UserController.class, UserSummaryDTO.class);
	}

	@Override
	public UserSummaryDTO toModel(User user) {
		UserSummaryDTO userSummaryDto = createModelWithId(user.getId(), user);
		modelMapper.map(user, userSummaryDto);

		userSummaryDto.add(algaLinks.linkToUsers("users"));
		userSummaryDto.add(algaLinks.linkToUserGroup(userSummaryDto.getId(), "user-groups"));

		return userSummaryDto;
	}

	@Override
	public CollectionModel<UserSummaryDTO> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToUsers());
	}

	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}

	public void copyToDomainObject(UserSummaryInput userSummaryInput, User user) {
		modelMapper.map(userSummaryInput, user);
	}

}
