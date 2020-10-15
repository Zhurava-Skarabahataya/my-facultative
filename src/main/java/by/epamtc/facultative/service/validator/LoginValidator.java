package by.epamtc.facultative.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class LoginValidator implements Validator {

	private static final LoginValidator instance = new LoginValidator();

	private PasswordValidator nextRegistrationValidator = PasswordValidator.getInstance();
	private PasswordValidator nextAuthoricationValidator = PasswordValidator.getInstance();
	
	private static final String LOGIN_PATTERN_REGEX = "[а-яА-ЯйЙa-zA-Z0-9-_]$";
	Pattern loginPattern;
	
	

	private LoginValidator() {
		loginPattern = Pattern.compile(LOGIN_PATTERN_REGEX);
	}

	public static LoginValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo info) {

		String login = info.getUserLogin();

		if (login == null) {

			return "Login is empty.";

		}
		if (!loginPattern.matcher(login).find()) {
			return "Можно использовать буквы, цифры, тире и знак подчёркивания";
		}
		
		if (login.length() > 45) {
			return "Логин слишком длинный.";
		}

		return nextRegistrationValidator.validate(info);
	}

	public String validate(UserAuthorizationInfo data) {

		String login = data.getLogin();

		if (login == null) {

			return "Login is empty.";

		}
		if (!loginPattern.matcher(login).find()) {
			return "Можно использовать буквы, цифры, тире и знак подчёркивания";
		}
		return nextAuthoricationValidator.validate(data);

	}

}
