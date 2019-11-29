package com.algaworks.algafood.api.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PermissionDTO;
import com.algaworks.algafood.domain.model.Permission;

@Component
public class PermissionMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissionDTO toDto(Permission permission) {
		return modelMapper.map(permission, PermissionDTO.class);
	}
	
	public List<PermissionDTO> toCollectionDto(Collection<Permission> permissions) {
		return permissions.stream().map(permission -> toDto(permission)).collect(Collectors.toList());
	}

}
