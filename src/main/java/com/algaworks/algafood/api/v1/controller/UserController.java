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

import com.algaworks.algafood.api.v1.controller.openapi.controller.UserControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.UserInput;
import com.algaworks.algafood.api.v1.model.input.UserPasswordInput;
import com.algaworks.algafood.api.v1.model.input.UserSummaryInput;
import com.algaworks.algafood.core.security.Security;
import com.algaworks.algafood.api.v1.mapper.UserMapper;
import com.algaworks.algafood.api.v1.model.UserSummaryDTO;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Override
	@Security.UsersGroupsPermissions.allowedConsult
	@GetMapping
	public CollectionModel<UserSummaryDTO> findAll() {
		return userMapper.toCollectionModel(userService.findAll());
	}

	@Override
	@Security.UsersGroupsPermissions.allowedConsult
	@GetMapping("/{userId}")
	public UserSummaryDTO findById(@PathVariable Long userId) {
		return userMapper.toModel(userService.findById(userId));
	}

	@Override
	@Security.UsersGroupsPermissions.allowedEdit
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserSummaryDTO save(@RequestBody @Valid UserInput userInput) {
		User user = userMapper.toDomainObject(userInput);
		return userMapper.toModel(userService.save(user));
	}

	@Override
	@Security.UsersGroupsPermissions.allowedUpdateUser
	@PutMapping("/{userId}")
	public UserSummaryDTO update(@PathVariable Long userId, @RequestBody @Valid UserSummaryInput userSummaryInput) {
		User userCurrent = userService.findById(userId);

		userMapper.copyToDomainObject(userSummaryInput, userCurrent);

		return userMapper.toModel(userService.save(userCurrent));
	}

	@Override
	@Security.UsersGroupsPermissions.allowedUpdatePassword
	@PutMapping("/{userId}/password")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable Long userId, @RequestBody @Valid UserPasswordInput userPasswordInput) {
		userService.updatePassword(userId, userPasswordInput.getCurrentPassword(), userPasswordInput.getNewPassword());
	}

	@Override
	@Security.UsersGroupsPermissions.allowedEdit
	@DeleteMapping("/{userId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId) {
		userService.delete(userId);
	}

}
