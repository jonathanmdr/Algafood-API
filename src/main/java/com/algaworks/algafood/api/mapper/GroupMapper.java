package com.algaworks.algafood.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.GroupController;
import com.algaworks.algafood.api.model.GroupDTO;
import com.algaworks.algafood.api.model.input.GroupInput;
import com.algaworks.algafood.domain.model.Group;

@Component
public class GroupMapper extends RepresentationModelAssemblerSupport<Group, GroupDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public GroupMapper() {
		super(GroupController.class, GroupDTO.class);
	}

	@Override
	public GroupDTO toModel(Group group) {
		GroupDTO groupDto = createModelWithId(group.getId(), group);
		modelMapper.map(group, groupDto);

		groupDto.add(algaLinks.linkToGroups("groups"));
		groupDto.add(algaLinks.linkToGroupPermissions(group.getId(), "permissions"));

		return groupDto;
	}

	@Override
	public CollectionModel<GroupDTO> toCollectionModel(Iterable<? extends Group> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToGroups());
	}

	public Group toDomainObject(GroupInput groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}

	public void copyToDomainObject(GroupInput groupInput, Group group) {
		modelMapper.map(groupInput, group);
	}

}
