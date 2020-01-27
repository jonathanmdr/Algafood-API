package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.controller.openapi.controller.GroupControllerOpenApi;
import com.algaworks.algafood.api.mapper.GroupMapper;
import com.algaworks.algafood.api.model.GroupDTO;
import com.algaworks.algafood.api.model.input.GroupInput;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;

@RestController
@RequestMapping(path = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController implements GroupControllerOpenApi {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupMapper groupMapper;
	
	@GetMapping
	public List<GroupDTO> findAll() {
		return groupMapper.toCollectionDto(groupService.findAll());
	}
	
	@GetMapping("/{groupId}")
	public GroupDTO findById(@PathVariable Long groupId) {
		return groupMapper.toDto(groupService.findById(groupId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public GroupDTO save(@RequestBody @Valid GroupInput groupInput) {
		Group group = groupMapper.toDomainObject(groupInput);
		return groupMapper.toDto(groupService.save(group));
	}
	
	@PutMapping("/{groupId}")
	public GroupDTO update(@PathVariable Long groupId, @RequestBody @Valid GroupInput groupInput) {
		Group groupCurrent = groupService.findById(groupId);
		
		groupMapper.copyToDomainObject(groupInput, groupCurrent);
		
		return groupMapper.toDto(groupService.save(groupCurrent));
	}
	
	@DeleteMapping("/{groupId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long groupId) {
		groupService.delete(groupId);
	}

}
