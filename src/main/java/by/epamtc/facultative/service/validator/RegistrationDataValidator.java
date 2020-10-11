package by.epamtc.facultative.service.validator;

import javax.xml.bind.ValidationException;

import by.epamtc.facultative.bean.UserRegistrationInfo;

public class RegistrationDataValidator implements RegistrationValidator {
	
	private static final RegistrationDataValidator instance = new RegistrationDataValidator();

	private UserNameValidator next = UserNameValidator.getInstance();//chain of responsibility или найти др паттерн?
	
	private RegistrationDataValidator() {
		
	}
	
	public static RegistrationDataValidator getInstance() {
		return instance;
	}
	

	public boolean validate(UserRegistrationInfo data) {
		
		if (data == null) {
			//throw new ValidationException("No info about user!");
			return false;
		}
		
		return next.validate(data);

	}

}
