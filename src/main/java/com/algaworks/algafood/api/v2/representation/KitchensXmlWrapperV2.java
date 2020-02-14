package com.algaworks.algafood.api.v2.representation;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v2.model.KitchenDTOV2;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "kitchens")
@Data
public class KitchensXmlWrapperV2 {

	@JacksonXmlProperty(localName = "kitchen")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private CollectionModel<KitchenDTOV2> kitchens;

}
