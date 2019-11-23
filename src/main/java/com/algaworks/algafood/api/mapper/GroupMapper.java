package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.GroupDTO;
import com.algaworks.algafood.api.model.input.GroupInput;
import com.algaworks.algafood.domain.model.Group;

@Component
public class GroupMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GroupDTO toDto(Group group) {
		return modelMapper.map(group, GroupDTO.class);
	}
	
	public List<GroupDTO> toCollectionDto(List<Group> groups) {
		return groups.stream().map(group -> toDto(group)).collect(Collectors.toList());
	}
	
	public Group toDomainObject(GroupInput groupInput) {
		return modelMapper.map(groupInput, Group.class);
	}
	
	public void copyToDomainObject(GroupInput groupInput, Group group) {
		modelMapper.map(groupInput, group);
	}

}
