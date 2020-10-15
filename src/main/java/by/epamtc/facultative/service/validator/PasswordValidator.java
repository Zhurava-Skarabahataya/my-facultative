package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class PasswordValidator implements Validator {

	private static final PasswordValidator instance = new PasswordValidator();

	private final EmailValidator next = EmailValidator.getInstance();

	private static final String PASSWORD_PATTERN_REGEX = "^[а-яА-ЯйЙa-zA-Z0-9]{6,}$";
	Pattern passwordPattern;

	private PasswordValidator() {
		passwordPattern = Pattern.compile(PASSWORD_PATTERN_REGEX);
	}

	public static PasswordValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo info) {

		String password = info.getUserPassword();

		if (password == null || password.length() < 6) {
			return "Пароль слишком короткий";
			
		} 
		
		if (!passwordPattern.matcher(password).find()) {
			return "Пароль может содержать только букв и цифры";
		}

		return next.validate(info);

	}

	public String validate(UserAuthorizationInfo data) {

		String password = data.getPassword();

		if (password == null || password.length() < 6) {
			return "Пароль слишком короткий";
		}
		if (!passwordPattern.matcher(password).find()) {
			return "Пароль может содержать только букв и цифры";
		}
		return "OK";

	}

}
