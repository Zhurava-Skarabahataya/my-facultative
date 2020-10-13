package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class UserDataValidator implements Validator{

	private static final UserDataValidator instance = new UserDataValidator();

	private LoginValidator nextRegistrationValidator = LoginValidator.getInstance();
	private LoginValidator nextAuthoricationValidator = LoginValidator.getInstance();

	private UserDataValidator() {

	}

	public static UserDataValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo data) {

		if (data == null) {
			return "Data is empty";
		}

		return nextRegistrationValidator.validate(data);

	}

	public String validate(UserAuthorizationInfo data) {

		if (data == null) {
			return "Data is empty";
		}

		return nextAuthoricationValidator.validate(data);

	}

}
