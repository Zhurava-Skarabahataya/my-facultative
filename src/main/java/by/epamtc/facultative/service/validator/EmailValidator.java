package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserRegistrationInfo;

public class EmailValidator implements Validator {

	private static final EmailValidator instance = new EmailValidator();
	
	private final NameValidator next = NameValidator.getInstance();

	private static final String EMAIL_PATTERN_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	Pattern emailPattern;

	private EmailValidator() {
		emailPattern = Pattern.compile(EMAIL_PATTERN_REGEX);
	}

	public static EmailValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo info) {
		
		String email = info.getUserEmail();
		
		if (email == null) {
			return "Email is empty";
		}
		if (!emailPattern.matcher(email).find()) {
			return "E-mail не соответствует нормам написания.";
		}
		
		return next.validate(info);
	}

}
