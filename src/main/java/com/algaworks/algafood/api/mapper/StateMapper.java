package com.algaworks.algafood.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.StateController;
import com.algaworks.algafood.api.model.StateDTO;
import com.algaworks.algafood.api.model.input.StateInput;
import com.algaworks.algafood.domain.model.State;

@Component
public class StateMapper extends RepresentationModelAssemblerSupport<State, StateDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public StateMapper() {
		super(StateController.class, StateDTO.class);
	}

	@Override
	public StateDTO toModel(State state) {
		StateDTO stateDto = createModelWithId(state.getId(), state);
		modelMapper.map(state, stateDto);

		stateDto.add(linkTo(methodOn(StateController.class).findAll()).withRel("states"));

		return stateDto;
	}

	@Override
	public CollectionModel<StateDTO> toCollectionModel(Iterable<? extends State> entities) {
		return super.toCollectionModel(entities).add(linkTo(StateController.class).withSelfRel());
	}

	public State toDomainObject(StateInput stateInput) {
		return modelMapper.map(stateInput, State.class);
	}

	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}

}
