package com.algaworks.algafood.api.v1.controller;

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

import com.algaworks.algafood.api.v1.controller.openapi.controller.UserGroupControllerOpenApi;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.mapper.GroupMapper;
import com.algaworks.algafood.api.v1.model.GroupDTO;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

@RestController
@RequestMapping(path = "/v1/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<GroupDTO> findAllByUserId(@PathVariable Long userId) {
		User user = userService.findById(userId);

		CollectionModel<GroupDTO> groupDto = groupMapper
		        .toCollectionModel(user.getGroups())
		        .removeLinks()
		        .add(algaLinks.linkToUserGroup(userId))
		        .add(algaLinks.linkToUserGroupAssociate(userId, null, "associate"));
		        

		groupDto.forEach(group -> {
			group.add(algaLinks.linkToUserGroupDisassociate(userId, group.getId(), "disassociate"));
		});

		return groupDto;
	}

	@Override
	@PutMapping("/{groupId}")
	public ResponseEntity<Void> associateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.associateGroup(userId, groupId);

		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping("/{groupId}")
	public ResponseEntity<Void> disassociateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.disassociateGroup(userId, groupId);

		return ResponseEntity.noContent().build();
	}

}
