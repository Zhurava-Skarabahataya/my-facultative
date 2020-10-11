package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class UserNameValidator implements RegistrationValidator{

	private static final UserNameValidator instance = new UserNameValidator();

	private RegistrationValidator next = PasswordValidator.getInstance();
	
	private UserNameValidator() {
		
	}
	
	public static UserNameValidator getInstance() {
		return instance;
	}
	
	public boolean validate(UserRegistrationInfo info) {
		
		String firstName = info.getFirstName();
		String secondName = info.getSecondName();
		
		if (firstName == null ||secondName == null) {
		
			return false;
								
		}
		
		return next.validate(info);
	}

}
