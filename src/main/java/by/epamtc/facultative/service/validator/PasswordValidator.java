package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class PasswordValidator implements Validator {

	private static final PasswordValidator instance = new PasswordValidator();

	private final EmailValidator next = EmailValidator.getInstance();

	private static final String PASSWORD_PATTERN_REGEX = "^[а-яА-ЯйЙa-zA-Z0-9]{6,}$";

	private final String PASSWORD_IS_TOO_SHORT = "password_is_too_short";
	private final String PASSWORD_MUST_CONTAIN_LETTER_AND_NUMBERS = "password_contains_only_letters_and_numbers";
	private final String PASSWORD_IS_TOO_LONG = "password_is_too_long";

	private Pattern passwordPattern;

	private PasswordValidator() {
		passwordPattern = Pattern.compile(PASSWORD_PATTERN_REGEX);
	}

	public static PasswordValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo info) {

		String password = info.getUserPassword();

		if (password == null || password.length() < 6) {
			return PASSWORD_IS_TOO_SHORT;

		}

		if (!passwordPattern.matcher(password).find()) {
			return PASSWORD_MUST_CONTAIN_LETTER_AND_NUMBERS;
		}

		if (password.length() > 45) {
			return PASSWORD_IS_TOO_LONG;
		}

		return next.validate(info);

	}

	public String validate(UserAuthorizationInfo data) {

		String password = data.getPassword();

		if (password == null || password.length() < 6) {
			return PASSWORD_IS_TOO_SHORT;

		}

		if (!passwordPattern.matcher(password).find()) {
			return PASSWORD_MUST_CONTAIN_LETTER_AND_NUMBERS;
		}

		if (password.length() > 45) {
			return PASSWORD_IS_TOO_LONG;
		}

		return null;

	}

}
