package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserRegistrationInfo;

public class NameValidator implements Validator {

	private static final NameValidator instance = new NameValidator();

	private final String NAME_PATTERN_REGEX = "[a-zA-Zа-яА-ЯйЙ]";

	private final String MESSAGE_NAMES_ARE_EMPTY = "first_and_second_name_must_not_be_empty";
	private final String MESSAGE_FIRST_NAME_MUST_CONTAIN_ONLY_LETTERS = "first_name_must_contain_only_letters";
	private final String MESSAGE_SECOND_NAME_MUST_CONTAIN_ONLY_LETTERS = "second_name_must_contain_only_letters";
	private final String MESSAGE_PATRONYMIC_NAME_MUST_CONTAIN_ONLY_LETTERS = "patronymic_must_contain_only_letters";
	private final String MESSAGE_NAMES_ARE_LOO_LONG = "first_and_second_name_must_be_shorter_45_symbols";

	private Pattern emailPattern;

	private NameValidator() {
		emailPattern = Pattern.compile(NAME_PATTERN_REGEX);
	}

	public static NameValidator getInstance() {
		return instance;
	}

	public String validate(UserRegistrationInfo info) {

		String firstName = info.getFirstName();
		String secondName = info.getSecondName();
		String patronymic = info.getPatromynic();

		if (firstName == null || secondName == null) {
			return MESSAGE_NAMES_ARE_EMPTY;
		}
		if (!emailPattern.matcher(firstName).find()) {
			return MESSAGE_FIRST_NAME_MUST_CONTAIN_ONLY_LETTERS;
		}
		if (!emailPattern.matcher(secondName).find()) {
			return MESSAGE_SECOND_NAME_MUST_CONTAIN_ONLY_LETTERS;
		}
		if (patronymic.length() > 0 && !emailPattern.matcher(patronymic).find()) {
			return MESSAGE_PATRONYMIC_NAME_MUST_CONTAIN_ONLY_LETTERS;
		}
		if (firstName.length() > 45 || secondName.length() > 45 || patronymic.length() > 45) {
			return MESSAGE_NAMES_ARE_LOO_LONG;
		}

		return null;
	}

}
