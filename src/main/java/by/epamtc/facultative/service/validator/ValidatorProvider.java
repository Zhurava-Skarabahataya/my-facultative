package by.epamtc.facultative.service.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValidatorProvider {
	
	private static final ValidatorProvider instance = new ValidatorProvider();
	private Map<ArrayList<Validator>, ValidationScenarioName> validatorsMap = new HashMap<ArrayList<Validator>, ValidationScenarioName>();
	
	ArrayList<Validator> validatorsForRegistration = new ArrayList<Validator>();
	ArrayList<Validator> validatorsForAuthorization= new ArrayList<Validator>();
	
	
	private ValidatorProvider() {
	}
	
	public static ValidatorProvider getInstance() {
		
		return instance;
	}

}
