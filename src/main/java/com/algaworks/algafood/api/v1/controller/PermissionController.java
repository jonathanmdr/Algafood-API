package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.PermissionControllerOpenApi;
import com.algaworks.algafood.api.v1.mapper.PermissionMapper;
import com.algaworks.algafood.api.v1.model.PermissionDTO;
import com.algaworks.algafood.domain.service.PermissionService;

@RestController
@RequestMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	@GetMapping
	public CollectionModel<PermissionDTO> findAll() {
		return permissionMapper.toCollectionModel(permissionService.findAll());
	}

	@Override
	@GetMapping("/{permissionId}")
	public PermissionDTO findById(@PathVariable Long permissionId) {
		return permissionMapper.toModel(permissionService.findById(permissionId));
	}

}
