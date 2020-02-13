package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
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

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<PermissionDTO> findById(@PathVariable Long groupId) {
		Group group = groupService.findById(groupId);

		CollectionModel<PermissionDTO> permissionsDto = permissionMapper
				.toCollectionModel(group.getPermissions())
				.removeLinks()
				.add(algaLinks.linkToGroupPermissions(groupId))
				.add(algaLinks.linkToGroupPermissionAssociate(groupId, null, "associate"));

		permissionsDto.forEach(permission -> {
			permission.add(algaLinks.linkToGroupPermissionDisassociate(groupId, permission.getId(), "disassociate"));
		});

		return permissionsDto;
	}

	@Override
	@PutMapping("/{permissionId}")
	public ResponseEntity<Void> associatePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.associatePermission(groupId, permissionId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{permissionId}")
	public ResponseEntity<Void> disassociatePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
		groupService.disassociatePermission(groupId, permissionId);

		return ResponseEntity.noContent().build();
	}

}
