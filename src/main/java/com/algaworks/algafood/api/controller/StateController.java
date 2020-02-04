package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.controller.openapi.controller.StateControllerOpenApi;
import com.algaworks.algafood.api.mapper.StateMapper;
import com.algaworks.algafood.api.model.StateDTO;
import com.algaworks.algafood.api.model.input.StateInput;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.service.StateService;

@RestController
@RequestMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {

	@Autowired
	private StateService stateService;

	@Autowired
	private StateMapper stateMapper;

	@Override
	@GetMapping
	public CollectionModel<StateDTO> findAll() {
		return stateMapper.toCollectionModel(stateService.findAll());
	}

	@Override
	@GetMapping("/{stateId}")
	public StateDTO findById(@PathVariable Long stateId) {
		return stateMapper.toModel(stateService.findById(stateId));
	}

	@Override
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public StateDTO save(@RequestBody @Valid StateInput stateInput) {
		State state = stateMapper.toDomainObject(stateInput);
		return stateMapper.toModel(stateService.save(state));
	}

	@Override
	@PutMapping("/{stateId}")
	public StateDTO update(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
		State stateCurrent = stateService.findById(stateId);

		stateMapper.copyToDomainObject(stateInput, stateCurrent);

		return stateMapper.toModel(stateService.save(stateCurrent));
	}

	@Override
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long stateId) {
		stateService.delete(stateId);
	}

}
