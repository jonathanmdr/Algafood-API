package com.algaworks.algafood.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.controller.UserGroupController;
import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.api.model.input.UserInput;
import com.algaworks.algafood.api.model.input.UserSummaryInput;
import com.algaworks.algafood.domain.model.User;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<User, UserSummaryDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public UserMapper() {
		super(UserController.class, UserSummaryDTO.class);
	}

	@Override
	public UserSummaryDTO toModel(User user) {
		UserSummaryDTO userSummaryDto = createModelWithId(user.getId(), user);
		modelMapper.map(user, userSummaryDto);

		userSummaryDto.add(linkTo(methodOn(UserController.class).findAll()).withRel("users"));
		userSummaryDto.add(
				linkTo(methodOn(UserGroupController.class).findById(userSummaryDto.getId())).withRel("user-groups"));

		return userSummaryDto;
	}

	@Override
	public CollectionModel<UserSummaryDTO> toCollectionModel(Iterable<? extends User> entities) {
		return super.toCollectionModel(entities).add(linkTo(UserController.class).withSelfRel());
	}

	public User toDomainObject(UserInput userInput) {
		return modelMapper.map(userInput, User.class);
	}

	public void copyToDomainObject(UserSummaryInput userSummaryInput, User user) {
		modelMapper.map(userSummaryInput, user);
	}

}
