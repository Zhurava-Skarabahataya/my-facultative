package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.service.FullNameService;

public class FullNameServiceImpl implements FullNameService{

	private static final FullNameServiceImpl instance = new FullNameServiceImpl();
	
	private FullNameServiceImpl() {
		
	}
	
	public static FullNameServiceImpl getInstance () {
		return instance;
	}
	
	@Override
	public String createFullName (String firstName, String secondName, String patronymic) {
		
		StringBuilder fullName;
		fullName = new StringBuilder();
		
		fullName.append(firstName);
		
		if (patronymic != null) {
			fullName.append(" ");
			fullName.append(patronymic);
		}

		fullName.append(" ");
		fullName.append(secondName);

		
		return fullName.toString();
		
	}


}
