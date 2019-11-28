package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.mapper.UserMapper;
import com.algaworks.algafood.api.model.UserSummaryDTO;
import com.algaworks.algafood.api.model.input.UserInput;
import com.algaworks.algafood.api.model.input.UserPasswordInput;
import com.algaworks.algafood.api.model.input.UserSummaryInput;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping
	public List<UserSummaryDTO> findAll() {
		return userMapper.toCollectionSummaryDto(userService.findAll());
	}
	
	@GetMapping("/{userId}")
	public UserSummaryDTO findById(@PathVariable Long userId) {
		return userMapper.toSummaryDto(userService.findById(userId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserSummaryDTO save(@RequestBody @Valid UserInput userInput) {
		User user = userMapper.toDomainObject(userInput);
		return userMapper.toSummaryDto(userService.save(user));
	}
	
	@PutMapping("/{userId}")
	public UserSummaryDTO update(@PathVariable Long userId, @RequestBody @Valid UserSummaryInput userSummaryInput) {
		User userCurrent = userService.findById(userId);
		
		userMapper.copyToDomainObject(userSummaryInput, userCurrent);
		
		return userMapper.toSummaryDto(userService.save(userCurrent));
	}
	
	@PutMapping("/{userId}/password")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable Long userId, @RequestBody @Valid UserPasswordInput userPasswordInput) {
		userService.updatePassword(userId, userPasswordInput.getCurrentPassword(), userPasswordInput.getNewPassword());
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId) {
		userService.delete(userId);
	}

}
