package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.api.representation.KitchensXmlWrapper;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {
	
	@Autowired
	private KitchenService kitchenService;
	
	@GetMapping
	public List<Kitchen> findAll() {
		return kitchenService.findAll();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper findAllInFormatXml() {
		return new KitchensXmlWrapper(kitchenService.findAll());
	}
	
	@GetMapping("/{kitchenId}")
	public Kitchen findById(@PathVariable Long kitchenId) {
		return kitchenService.findById(kitchenId);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Kitchen save(@RequestBody @Valid Kitchen kitchen) {
		return kitchenService.save(kitchen);
	}
	
	@PutMapping("/{kitchenId}")
	public Kitchen update(@PathVariable Long kitchenId, @RequestBody @Valid Kitchen kitchen) {
		Kitchen kitchenCurrent = kitchenService.findById(kitchenId);
		
		BeanUtils.copyProperties(kitchen, kitchenCurrent, "id");
		
		return kitchenService.save(kitchenCurrent);		
	}
	
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		kitchenService.delete(kitchenId);
	}

}
