package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public interface RegistrationValidator {

	boolean validate(UserRegistrationInfo info);
	
}
