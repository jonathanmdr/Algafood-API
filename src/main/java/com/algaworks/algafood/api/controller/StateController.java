package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateService;

@RestController
@RequestMapping("/states")
public class StateController {
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private StateService stateService;
	
	@GetMapping
	public List<State> findAll() {
		return stateRepository.findAll();
	}
	
	@GetMapping("/{stateId}")
	public ResponseEntity<State> findById(@PathVariable Long stateId) {
		Optional<State> state = stateRepository.findById(stateId);
		return state.isPresent() ? ResponseEntity.ok(state.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public State save(@RequestBody State state) {
		return stateService.save(state);
	}
	
	@PutMapping("/{stateId}")
	public ResponseEntity<State> update(@PathVariable Long stateId, @RequestBody State state) {		
		Optional<State> stateCurrent = stateRepository.findById(stateId);
		
		if (stateCurrent.isPresent()) {
			BeanUtils.copyProperties(state, stateCurrent.get(), "id");
			State stateSaved = stateService.save(stateCurrent.get());
			return ResponseEntity.ok(stateSaved);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{stateId}")
	public ResponseEntity<State> delete(@PathVariable Long stateId) {
		try {
			stateService.delete(stateId);
			return ResponseEntity.noContent().build();
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntityInUseException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
