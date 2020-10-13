package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class PasswordValidator implements Validator{

	private static final PasswordValidator instance = new PasswordValidator();
	
	private static final String PASSWORD_PATTERN_REGEX = "[a-zA-Z0-9]{6,}$";
	Pattern passwordPattern;
	
	
	private PasswordValidator() {
		passwordPattern = Pattern.compile(PASSWORD_PATTERN_REGEX);
	}

	public static PasswordValidator getInstance() {
		return instance;
	}
	
	public String validate(UserRegistrationInfo info) {
		
		String password = info.getUserPassword();
		
		if (password == null || password.length() <6) {
			return "Пароль слишком короткий";
		}
		else if (!passwordPattern.matcher(password).find()) {
				return "Пароль может содержать только букв и цифры";
			}
			
		
		return null;
		
	}
	
	public String validate(UserAuthorizationInfo data) {

		if (data == null) {
			return "Data is empty";
		}
		return null;

	}

}
