package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.controller.openapi.controller.UserGroupControllerOpenApi;
import com.algaworks.algafood.api.mapper.GroupMapper;
import com.algaworks.algafood.api.model.GroupDTO;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

@RestController
@RequestMapping(path = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupMapper groupMapper;
	
	@GetMapping
	public CollectionModel<GroupDTO> findAllByUserId(@PathVariable Long userId) {
		User user = userService.findById(userId);
		return groupMapper.toCollectionModel(user.getGroups());
	}
	
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.associateGroup(userId, groupId);
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disassociateGroup(@PathVariable Long userId, @PathVariable Long groupId) {
		userService.disassociateGroup(userId, groupId);
	}

}
