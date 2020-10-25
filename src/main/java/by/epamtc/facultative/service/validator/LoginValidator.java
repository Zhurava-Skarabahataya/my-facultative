package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class LoginValidator implements Validator {

	private static final LoginValidator instance = new LoginValidator();

	private final PasswordValidator nextRegistrationValidator = PasswordValidator.getInstance();
	private final PasswordValidator nextAuthoricationValidator = PasswordValidator.getInstance();
	
	private final String MESSAGE_LOGIN_IS_EMPTY = "login_is_empty";
	private final String MESSAGE_LOGIN_MUST_CONTAIN_SYMBOLS = "login_must_contain_letters_numbers_and_underscore";
	private final String MESSAGE_LOGIN_IS_TOO_LONG = "login_is_too_long";
	
	private final String LOGIN_PATTERN_REGEX = "[а-яА-ЯйЙa-zA-Z0-9-_]$";
	
	private Pattern loginPattern;
	
	

	private LoginValidator() {
		loginPattern = Pattern.compile(LOGIN_PATTERN_REGEX);
	}

	public static LoginValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo user) {

		String login = user.getUserLogin();

		if (login == null) {

			return MESSAGE_LOGIN_IS_EMPTY;

		}
		if (!loginPattern.matcher(login).find()) {
			return MESSAGE_LOGIN_MUST_CONTAIN_SYMBOLS;
		}
		
		if (login.length() > 45) {
			return MESSAGE_LOGIN_IS_TOO_LONG;
		}

		return nextRegistrationValidator.validate(user);
	}

	public String validate(UserAuthorizationInfo user) {

		String login = user.getLogin();

		if (login == null) {

			return MESSAGE_LOGIN_IS_EMPTY;

		}
		if (!loginPattern.matcher(login).find()) {
			return MESSAGE_LOGIN_MUST_CONTAIN_SYMBOLS;
		}
		
		if (login.length() > 45) {
			return MESSAGE_LOGIN_IS_TOO_LONG;
		}

		
		return nextAuthoricationValidator.validate(user);

	}

}
