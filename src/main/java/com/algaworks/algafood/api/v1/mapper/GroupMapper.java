package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GroupInput;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.GroupController;
import com.algaworks.algafood.api.v1.model.GroupDTO;
import com.algaworks.algafood.domain.model.Group;

@Component
public class GroupMapper extends RepresentationModelAssemblerSupport<Group, GroupDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public GroupMapper() {
		super(GroupController.class, GroupDTO.class);
	}

	@Override
	public GroupDTO toModel(Group group) {
		GroupDTO groupDto = createModelWithId(group.getId(), group);
		modelMapper.map(group, groupDto);

		if (algaSecurity.canConsultingUsersGroupsPermissions()) {
		    groupDto.add(algaLinks.linkToGroups("groups"));
		    groupDto.add(algaLinks.linkToGroupPermissions(group.getId(), "permissions"));
		}

		return groupDto;
	}

	@Override
	public CollectionModel<GroupDTO> toCollectionModel(Iterable<? extends Group> entities) {
	    CollectionModel<GroupDTO> groupsModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingUsersGroupsPermissions()) {
	        groupsModel.add(algaLinks.linkToGroups());
	    }
	    
	    return groupsModel;
	}

	public Group toDomainObject(GroupInput groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}

	public void copyToDomainObject(GroupInput groupInput, Group group) {
		modelMapper.map(groupInput, group);
	}

}
