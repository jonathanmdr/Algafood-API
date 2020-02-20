package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.controller.openapi.controller.KitchenControllerOpenApi;
import com.algaworks.algafood.api.v1.model.input.KitchenInput;
import com.algaworks.algafood.api.v1.representation.KitchensXmlWrapper;
import com.algaworks.algafood.api.v1.mapper.KitchenMapper;
import com.algaworks.algafood.api.v1.model.KitchenDTO;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping(path = "/v1/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private KitchenMapper kitchenMapper;

	@Autowired
	private PagedResourcesAssembler<Kitchen> pagedResourceAssembler;

	@Override
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public PagedModel<KitchenDTO> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Kitchen> kitchens = kitchenService.findAll(pageable);

		PagedModel<KitchenDTO> kitchensPagedModel = pagedResourceAssembler.toModel(kitchens, kitchenMapper);

		return kitchensPagedModel;
	}
	
	@Override
	@PreAuthorize("isAuthenticated()")
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public KitchensXmlWrapper findAllInFormatXml(Pageable pageable) {
		return new KitchensXmlWrapper(kitchenMapper.toCollectionModel(kitchenService.findAll(pageable).getContent()));
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{kitchenId}")
	public KitchenDTO findById(@PathVariable Long kitchenId) {
		return kitchenMapper.toModel(kitchenService.findById(kitchenId));
	}

	@Override
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS') and scope('write')")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public KitchenDTO save(@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen = kitchenMapper.toDomainObject(kitchenInput);
		return kitchenMapper.toModel(kitchenService.save(kitchen));
	}

	@Override
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS') and scope('write')")
	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchenCurrent = kitchenService.findById(kitchenId);

		kitchenMapper.copyToDomainObject(kitchenInput, kitchenCurrent);

		return kitchenMapper.toModel(kitchenService.save(kitchenCurrent));
	}

	@Override
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS') and scope('write')")
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		kitchenService.delete(kitchenId);
	}

}
