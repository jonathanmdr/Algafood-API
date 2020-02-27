package com.algaworks.algafood.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.StateInput;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.StateController;
import com.algaworks.algafood.api.v1.model.StateDTO;
import com.algaworks.algafood.domain.model.State;

@Component
public class StateMapper extends RepresentationModelAssemblerSupport<State, StateDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public StateMapper() {
		super(StateController.class, StateDTO.class);
	}

	@Override
	public StateDTO toModel(State state) {
		StateDTO stateDto = createModelWithId(state.getId(), state);
		modelMapper.map(state, stateDto);

		if (algaSecurity.canConsultingStates()) {
		    stateDto.add(algaLinks.linkToStates("states"));
		}

		return stateDto;
	}

	@Override
	public CollectionModel<StateDTO> toCollectionModel(Iterable<? extends State> entities) {
	    CollectionModel<StateDTO> statesModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.canConsultingStates()) {
	        statesModel.add(algaLinks.linkToStates());
	    }
	    
	    return statesModel;
	}

	public State toDomainObject(StateInput stateInput) {
		return modelMapper.map(stateInput, State.class);
	}

	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}

}
