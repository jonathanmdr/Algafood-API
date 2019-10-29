package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafood.api.representation.KitchensXmlWrapper;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {
	
	@Autowired
	private KitchenRepository kitchenRepository;
	
	@Autowired
	private KitchenService kitchenService;
	
	@GetMapping
	public List<Kitchen> findAll() {
		return kitchenRepository.findAll();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper findAllInFormatXml() {
		return new KitchensXmlWrapper(kitchenRepository.findAll());
	}
	
	@GetMapping("/{kitchenId}")
	public ResponseEntity<Kitchen> findById(@PathVariable Long kitchenId) {
		Optional<Kitchen> kitchen = kitchenRepository.findById(kitchenId);
		return kitchen.isPresent() ? ResponseEntity.ok(kitchen.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Kitchen save(@RequestBody Kitchen kitchen) {
		return kitchenService.save(kitchen);
	}
	
	@PutMapping("/{kitchenId}")
	public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
		Optional<Kitchen> kitchenCurrent = kitchenRepository.findById(kitchenId);
		
		if (kitchenCurrent.isPresent()) {
			BeanUtils.copyProperties(kitchen, kitchenCurrent.get(), "id");
			Kitchen kitchenSaved = kitchenService.save(kitchenCurrent.get());
			return ResponseEntity.ok(kitchenSaved);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{kitchenId}")	
	public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {
		try {
			kitchenService.delete(kitchenId);			
			return ResponseEntity.noContent().build();
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntityInUseException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
