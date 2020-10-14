package by.epamtc.facultative.service.validator;

import java.util.regex.Pattern;

import by.epamtc.facultative.bean.UserRegistrationInfo;

public class NameValidator implements Validator {

	private static final NameValidator instance = new NameValidator();

	private static final String NAME_PATTERN_REGEX = "[a-zA-Zа-яА-ЯйЙ]";

	Pattern emailPattern;

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
			return "Поля имени и фамилии должны быть заполнены";
		}
		if (!emailPattern.matcher(firstName).find()) {
			return "В имени могут содержаться только буквы";
		}
		if (!emailPattern.matcher(secondName).find()) {
			return "В фамилии могут содержаться только буквы";
		}
		if (patronymic.length() > 0 && !emailPattern.matcher(patronymic).find()) {
			System.out.println(patronymic);
			return "В отчестве могут содержаться только буквы";
		}

		return null;
	}

}
