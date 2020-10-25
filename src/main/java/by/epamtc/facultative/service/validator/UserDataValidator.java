package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class UserDataValidator implements Validator{

	private static final UserDataValidator instance = new UserDataValidator();

	private LoginValidator nextRegistrationValidator = LoginValidator.getInstance();
	private LoginValidator nextAuthoricationValidator = LoginValidator.getInstance();
	
	private final String MESSAGE_DATA_IS_EMPTY = "data_is_empty";

	private UserDataValidator() {

	}

	public static UserDataValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo data) {

		if (data == null) {
			return MESSAGE_DATA_IS_EMPTY;
		}

		return nextRegistrationValidator.validate(data);

	}

	public String validate(UserAuthorizationInfo data) {

		if (data == null) {
			return MESSAGE_DATA_IS_EMPTY;
		}

		return nextAuthoricationValidator.validate(data);

	}

}
