package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algaworks.algafood.api.controller.openapi.controller.KitchenControllerOpenApi;
import com.algaworks.algafood.api.mapper.KitchenMapper;
import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.input.KitchenInput;
import com.algaworks.algafood.api.representation.KitchensXmlWrapper;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping(path = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {
	
	@Autowired
	private KitchenService kitchenService;
	
	@Autowired
	private KitchenMapper kitchenMapper;
	
	@GetMapping
	public Page<KitchenDTO> findAll(Pageable pageable) {
		Page<Kitchen> kitchens = kitchenService.findAll(pageable);
		List<KitchenDTO> kitchensDTO = kitchenMapper.toCollectionDto(kitchens.getContent());
		return new PageImpl<>(kitchensDTO, pageable, kitchens.getTotalElements());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper findAllInFormatXml(Pageable pageable) {
		return new KitchensXmlWrapper(kitchenMapper.toCollectionDto(kitchenService.findAll(pageable).getContent()));
	}
	
	@GetMapping("/{kitchenId}")
	public KitchenDTO findById(@PathVariable Long kitchenId) {
		return kitchenMapper.toDto(kitchenService.findById(kitchenId));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public KitchenDTO save(@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen = kitchenMapper.toDomainObject(kitchenInput);
		return kitchenMapper.toDto(kitchenService.save(kitchen));
	}
	
	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchenCurrent = kitchenService.findById(kitchenId);
		
		kitchenMapper.copyToDomainObject(kitchenInput, kitchenCurrent);
		
		return kitchenMapper.toDto(kitchenService.save(kitchenCurrent));
	}
	
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		kitchenService.delete(kitchenId);
	}

}
