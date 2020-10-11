package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserRegistrationInfo;

public class PasswordValidator implements RegistrationValidator{

	private static final PasswordValidator instance = new PasswordValidator();
	
	private PasswordValidator() {
	}

	public static PasswordValidator getInstance() {
		return instance;
	}
	
	@Override
	public boolean validate(UserRegistrationInfo info) {
		
		String password = info.getUserPassword();
		
		if (password == null || password.length() <6) {
			return false;
			//password is too short
		}
		
		return true; //потом остальные валидаторы добавишь
	}

}
