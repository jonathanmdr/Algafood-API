package com.algaworks.algafood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.StateDTO;
import com.algaworks.algafood.api.model.input.StateInput;
import com.algaworks.algafood.domain.model.State;

@Component
public class StateMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public StateDTO toDto(State state) {
		return modelMapper.map(state, StateDTO.class);
	}
	
	public List<StateDTO> toCollectionDto(List<State> states) {
		return states.stream().map(state -> toDto(state)).collect(Collectors.toList());
	}
	
	public State toDomainObject(StateInput stateInput) {
		return modelMapper.map(stateInput, State.class);
	}
	
	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}

}
