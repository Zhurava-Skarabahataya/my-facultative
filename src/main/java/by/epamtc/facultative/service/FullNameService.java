package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.UserInfo;

public class FullNameService {

	private static final FullNameService instance = new FullNameService();
	
	private FullNameService() {
		
	}
	
	public static FullNameService getInstance () {
		return instance;
	}
	
	public String createFullName (String firstName, String secondName, String patronymic) {
		
		StringBuilder fullName = new StringBuilder();
		fullName.append(firstName);
		if (patronymic != null) {
			fullName.append(" ");
			fullName.append(patronymic);
			
		}
		fullName.append(" ");
		fullName.append(secondName);

		
		return fullName.toString();
		
	}

//	public void createFullNames(List<UserInfo> users) {
//		
//		for (UserInfo user: users) {
//			
//		}
//		
//	}
	
}
