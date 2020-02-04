package com.algaworks.algafood.api.representation;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.model.KitchenDTO;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "kitchens")
@Data
public class KitchensXmlWrapper {

	@JacksonXmlProperty(localName = "kitchen")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private CollectionModel<KitchenDTO> kitchens;

}
