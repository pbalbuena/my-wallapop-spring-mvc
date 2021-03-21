package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Oferta;

@Component
public class OfertaAddValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Oferta.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Oferta o = (Oferta) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "Error.empty");
		
		if (o.getPrecio() <= 0) {
			errors.rejectValue("precio", "Error.Oferta.precio");
		}
		
	}
}