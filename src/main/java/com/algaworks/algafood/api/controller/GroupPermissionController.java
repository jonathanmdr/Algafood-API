package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.controller.openapi.controller.GroupPermissionControllerOpenApi;
import com.algaworks.algafood.api.mapper.PermissionMapper;
import com.algaworks.algafood.api.model.PermissionDTO;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;

@RestController
@RequestMapping(path = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionController implements GroupPermissionControllerOpenApi {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@GetMapping
	public List<PermissionDTO> findById(@PathVariable Long groupId) {
		Group group = groupService.findById(groupId);
		return permissionMapper.toCollectionDto(group.getPermissions());
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associatePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.associatePermission(groupId, permissionId);
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociatePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.disassociatePermission(groupId, permissionId);
	}

}
