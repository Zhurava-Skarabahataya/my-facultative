package by.epamtc.facultative.service;

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
		fullName.append(" ");
		fullName.append(secondName);

		if (patronymic != null) {
			fullName.append(" ");
			fullName.append(patronymic);
			
		}
		
		return fullName.toString();
		
	}
	
}
