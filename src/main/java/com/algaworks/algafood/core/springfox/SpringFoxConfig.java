package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.v1.controller.openapi.model.CitiesModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.GroupsModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.KitchensModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.OrderSummaryModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.OrdersSummaryModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PaymentFormsModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PermissionsModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.ProductsModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.RestaurantsSummaryModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.StatesModelOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.UsersSummaryModelOpenApi;
import com.algaworks.algafood.api.exceptionhandler.ApiError;
import com.algaworks.algafood.api.v1.model.CityDTO;
import com.algaworks.algafood.api.v1.model.GroupDTO;
import com.algaworks.algafood.api.v1.model.KitchenDTO;
import com.algaworks.algafood.api.v1.model.OrderSummaryDTO;
import com.algaworks.algafood.api.v1.model.PaymentFormDTO;
import com.algaworks.algafood.api.v1.model.PermissionDTO;
import com.algaworks.algafood.api.v1.model.ProductDTO;
import com.algaworks.algafood.api.v1.model.RestaurantSummaryDTO;
import com.algaworks.algafood.api.v1.model.StateDTO;
import com.algaworks.algafood.api.v1.model.UserSummaryDTO;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
	
	@Bean
	public Docket apiDocket() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
				.globalResponseMessage(RequestMethod.POST, globalPostAndPutResponseMessage())
				.globalResponseMessage(RequestMethod.PUT, globalPostAndPutResponseMessage())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessage())
				.additionalModels(typeResolver.resolve(ApiError.class))
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, KitchenDTO.class), KitchensModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, OrderSummaryDTO.class), OrderSummaryModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CityDTO.class), CitiesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, StateDTO.class), StatesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PaymentFormDTO.class), PaymentFormsModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, GroupDTO.class), GroupsModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissionDTO.class), PermissionsModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, OrderSummaryDTO.class), OrdersSummaryModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProductDTO.class), ProductsModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, RestaurantSummaryDTO.class), RestaurantsSummaryModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, UserSummaryDTO.class), UsersSummaryModelOpenApi.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Realiza o gerencimanento de cidades"), 
					  new Tag("Grupos", "Realiza o gerenciamento de grupos de usuário"),
					  new Tag("Cozinhas", "Realiza o gerencimento de cozinhas"),
					  new Tag("Formas de pagamento", "Realiza o gerencimento de formas de pagamento"),
					  new Tag("Restaurantes", "Realiza o gerencimento de restaurantes"),
					  new Tag("Pedidos", "Realiza o gerencimento de pedidos"),					  
					  new Tag("Estados", "Realiza o gerencimento de estados"),
					  new Tag("Produtos", "Realiza o gerenciamento de produtos"),
					  new Tag("Usuários", "Realiza o gerenciamento de usuários"),
					  new Tag("Estatísticas", "Consulta estatísticas de vendas do Algafood"),
					  new Tag("Permissões", "Realiza o gerenciamento de permissões"));
	}
	
	private List<ResponseMessage> globalGetResponseMessage() {
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("ApiError"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possuí representação que poderia ser aceita pelo consumidor")
						.build()
				);
	}
	
	private List<ResponseMessage> globalPostAndPutResponseMessage() {
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.BAD_REQUEST.value())
						.message("Requisição inválida (erro do cliente)")
						.responseModel(new ModelRef("ApiError"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("ApiError"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso não possuí representação que poderia ser aceita pelo consumidor")
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Requisição recusada porque o corpo está em um formato não suportado")
						.responseModel(new ModelRef("ApiError"))
						.build()
				);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessage() {
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.BAD_REQUEST.value())
						.message("Requisição inválida (erro do cliente)")
						.responseModel(new ModelRef("ApiError"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("ApiError"))
						.build()
				);
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para clientes e restaurantes")
				.version("1.0.0")
				.contact(new Contact("Jonathan Henrique Medeiros", "https://jonathanmdr.github.io/", "jonathan.mdr@hotmail.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
