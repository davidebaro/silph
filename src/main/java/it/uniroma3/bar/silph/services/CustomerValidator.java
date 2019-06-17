package it.uniroma3.bar.silph.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.bar.silph.model.Customer;

@Component
public class CustomerValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "required");
		ValidationUtils.rejectIfEmpty(errors, "secondName", "required");
		ValidationUtils.rejectIfEmpty(errors, "email", "required");
		ValidationUtils.rejectIfEmpty(errors, "phone", "required");
		String phone = ((Customer) target).getPhone();
		for(char c:phone.toCharArray()) {
			if (c<'0' || c>'9') {
				errors.reject("invalid");
				break;
			}
		}
		String email = ((Customer)target).getEmail();
		boolean valid=false;
		for(char c:email.toCharArray()) {
			if (c=='@') {
				valid=true;
			}
		}
		if(!valid) {
			errors.reject("invalid");
		}
	}

}
