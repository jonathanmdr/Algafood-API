package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroIncludingDescriptionValidator implements ConstraintValidator<ValueZeroIncludingDescription, Object> {

	private String fieldValue;
	private String fieldDescription;
	private String requiredDescription;
	
	@Override
	public void initialize(ValueZeroIncludingDescription constraintAnnotation) {
		this.fieldValue = constraintAnnotation.fieldValue();
		this.fieldDescription = constraintAnnotation.fieldDescription();
		this.requiredDescription = constraintAnnotation.requiredDescription();
	}
	
	@Override
	public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
		boolean isValid = true;
		
		try {
			var value = BeanUtils.getPropertyDescriptor(objectValidation.getClass(), this.fieldValue)
					.getReadMethod().invoke(objectValidation);
			var field = BeanUtils.getPropertyDescriptor(objectValidation.getClass(), this.fieldDescription)
					.getReadMethod().invoke(objectValidation);
			
			if (value != null && BigDecimal.ZERO.compareTo((BigDecimal) value) == 0 && fieldDescription != null) {
				isValid = ((String) field).toLowerCase().contains(this.requiredDescription.toLowerCase());
			}
			
		} catch (Exception ex) {
			throw new ValidationException(ex);
		} 
		
		return isValid;
	}
	
}
