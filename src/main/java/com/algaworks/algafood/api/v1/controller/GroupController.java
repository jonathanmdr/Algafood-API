package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.GroupControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.GroupInput;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.api.v1.mapper.GroupMapper;
import com.algaworks.algafood.api.v1.model.GroupDTO;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;

@RestController
@RequestMapping(path = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController implements GroupControllerOpenApi {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupMapper groupMapper;
	
	@Override
	@Security.UsersGroupsPermissions.allowedConsult
	@GetMapping
	public CollectionModel<GroupDTO> findAll() {
		return groupMapper.toCollectionModel(groupService.findAll());
	}
	
	@Override
	@Security.UsersGroupsPermissions.allowedConsult
	@GetMapping("/{groupId}")
	public GroupDTO findById(@PathVariable Long groupId) {
		return groupMapper.toModel(groupService.findById(groupId));
	}
	
	@Override
	@Security.UsersGroupsPermissions.allowedEdit
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public GroupDTO save(@RequestBody @Valid GroupInput groupInput) {
		Group group = groupMapper.toDomainObject(groupInput);
		return groupMapper.toModel(groupService.save(group));
	}
	
	@Override
	@Security.UsersGroupsPermissions.allowedEdit
	@PutMapping("/{groupId}")
	public GroupDTO update(@PathVariable Long groupId, @RequestBody @Valid GroupInput groupInput) {
		Group groupCurrent = groupService.findById(groupId);
		
		groupMapper.copyToDomainObject(groupInput, groupCurrent);
		
		return groupMapper.toModel(groupService.save(groupCurrent));
	}
	
	@Override
	@Security.UsersGroupsPermissions.allowedEdit
	@DeleteMapping("/{groupId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long groupId) {
		groupService.delete(groupId);
	}

}
