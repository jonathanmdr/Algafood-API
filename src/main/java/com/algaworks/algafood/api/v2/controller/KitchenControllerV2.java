package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.v2.controller.openapi.KitchenControllerOpenApiV2;
import com.algaworks.algafood.api.v2.mapper.KitchenMapperV2;
import com.algaworks.algafood.api.v2.model.KitchenDTOV2;
import com.algaworks.algafood.api.v2.model.input.KitchenInputV2;
import com.algaworks.algafood.api.v2.representation.KitchensXmlWrapperV2;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping(path = "/v2/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenControllerV2 implements KitchenControllerOpenApiV2 {

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private KitchenMapperV2 kitchenMapper;

	@Autowired
	private PagedResourcesAssembler<Kitchen> pagedResourceAssembler;
	
	@GetMapping
	public PagedModel<KitchenDTOV2> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Kitchen> kitchens = kitchenService.findAll(pageable);

		PagedModel<KitchenDTOV2> kitchensPagedModel = pagedResourceAssembler.toModel(kitchens, kitchenMapper);

		return kitchensPagedModel;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapperV2 findAllInFormatXml(Pageable pageable) {
		return new KitchensXmlWrapperV2(kitchenMapper.toCollectionModel(kitchenService.findAll(pageable).getContent()));
	}

	@GetMapping("/{kitchenId}")
	public KitchenDTOV2 findById(@PathVariable Long kitchenId) {
		return kitchenMapper.toModel(kitchenService.findById(kitchenId));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public KitchenDTOV2 save(@RequestBody @Valid KitchenInputV2 kitchenInput) {
		Kitchen kitchen = kitchenMapper.toDomainObject(kitchenInput);
		return kitchenMapper.toModel(kitchenService.save(kitchen));
	}

	@PutMapping("/{kitchenId}")
	public KitchenDTOV2 update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInputV2 kitchenInput) {
		Kitchen kitchenCurrent = kitchenService.findById(kitchenId);

		kitchenMapper.copyToDomainObject(kitchenInput, kitchenCurrent);

		return kitchenMapper.toModel(kitchenService.save(kitchenCurrent));
	}

	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		kitchenService.delete(kitchenId);
	}

}
