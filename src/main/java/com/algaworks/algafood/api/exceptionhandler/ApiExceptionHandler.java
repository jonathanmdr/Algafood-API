package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String GENERIC_ERROR_MESSAGE = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente mais tarde, se o problema persistir, entre em contato "
			+ "com o administrador do sistema.";

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleStateNotFoundException(EntityNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ApiError error = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<Object> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;

		ApiError error = createApiErrorBuilder(status, ApiErrorType.ENTITY_IN_USE, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ApiError error = createApiErrorBuilder(status, ApiErrorType.BUSINESS_EXCEPTION, ex.getMessage())
				.userMessage(ex.getMessage())
				.build();

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<Object> handlerUncaught(Exception ex, WebRequest request) { 
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ex.printStackTrace();
	  
		ApiError error = createApiErrorBuilder(status, ApiErrorType.INTERNAL_SERVER_ERROR, GENERIC_ERROR_MESSAGE)
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();
	  
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request); 
	}	 

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,	HttpStatus status, WebRequest request) {
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());

		ApiError error = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, detail)
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		} else if (rootCause instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) rootCause, headers, status, request);
		}

		ApiError error = createApiErrorBuilder(status, ApiErrorType.REQUEST_BODY_ERROR,	"O corpo da requisição é inválido. Verifique erro de sintaxe.")
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um valor inválido."
						+ " Corrija e informe um valor compatível com o tipo de dado %s.",
						path, ex.getValue(), ex.getTargetType().getSimpleName());

		ApiError error = createApiErrorBuilder(status, ApiErrorType.REQUEST_BODY_ERROR, detail)
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,	HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' não existe.", path);

		ApiError error = createApiErrorBuilder(status, ApiErrorType.REQUEST_BODY_ERROR, detail)
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um valor inválido."
						+ " Corrija e informe um valor compatível com o tipo de dado %s", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		ApiError error = createApiErrorBuilder(status, ApiErrorType.REQUEST_INVALID_PARAMETER, detail)
				.userMessage(GENERIC_ERROR_MESSAGE)
				.build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (body == null) {
			body = ApiError.builder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.userMessage(GENERIC_ERROR_MESSAGE)
					.timestamp(LocalDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = ApiError.builder()
					.status(status.value())
					.title((String) body)
					.userMessage(GENERIC_ERROR_MESSAGE)
					.timestamp(LocalDateTime.now())
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType errorType, String detail) {
		return ApiError.builder()
				.status(status.value())
				.type(errorType.getUri())
				.title(errorType.getTitle())
				.detail(detail)
				.timestamp(LocalDateTime.now());
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}

}