package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PermissionController;
import com.algaworks.algafood.api.v1.model.PermissionDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Permission;

@Component
public class PermissionMapper extends RepresentationModelAssemblerSupport<Permission, PermissionDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public PermissionMapper() {
		super(PermissionController.class, PermissionDTO.class);
	}

	@Override
	public PermissionDTO toModel(Permission permission) {
		PermissionDTO permissionDto = createModelWithId(permission.getId(), permission);
		modelMapper.map(permission, permissionDto);

		if (algaSecurity.canConsultingUsersGroupsPermissions()) {
		    permissionDto.add(algaLinks.linkToPermissions("permissions"));
		}

		return permissionDto;
	}

	@Override
	public CollectionModel<PermissionDTO> toCollectionModel(Iterable<? extends Permission> entities) {
	    CollectionModel<PermissionDTO> permissionsModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingUsersGroupsPermissions()) {
	        permissionsModel.add(algaLinks.linkToPermissions());
	    }
	    
	    return permissionsModel;
	}

}
