package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "photos")
@ApiModel(value = "FotoProdutoDTO", description = "Representa os dados do arquivo de foto")
@Getter
@Setter
public class ProductPhotoDTO extends RepresentationModel<ProductPhotoDTO> {

	@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg", position = 10)
	private String fileName;

	@ApiModelProperty(example = "Prime Rib ao ponto", position = 20)
	private String description;

	@ApiModelProperty(example = "image/jpeg", position = 30)
	private String contentType;

	@ApiModelProperty(example = "202912", position = 40)
	private Long size;

}
