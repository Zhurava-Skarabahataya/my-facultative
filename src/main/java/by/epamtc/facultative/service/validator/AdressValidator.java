package by.epamtc.facultative.service.validator;

import by.epamtc.facultative.bean.UserInfo;

public class AdressValidator implements Validator {
	
	private static final AdressValidator instance = new AdressValidator();
	
	
	private final String MESSAGE_ADRESS_IS_TOO_LONG = "adress_is_too_long";


	
	private AdressValidator() {
		
	}
	
	public static AdressValidator getInstance() {
		return instance;
	}

	public String validate(UserInfo userPageInfo) {
		
		String adress = userPageInfo.getUserAdress();
		
		if (adress.length() > 200) {
			return MESSAGE_ADRESS_IS_TOO_LONG;
		}
		
		return null;
	}

	
	
}
