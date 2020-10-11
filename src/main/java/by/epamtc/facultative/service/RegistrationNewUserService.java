package by.epamtc.facultative.service;

import by.epamtc.facultative.DAO.InfoCheckerDAO;
import by.epamtc.facultative.DAO.RegistrationDAO;
import by.epamtc.facultative.DAO.impl.EmailCheckerDAOImlp;
import by.epamtc.facultative.DAO.impl.LoginCheckerDAOImpl;
import by.epamtc.facultative.DAO.impl.RegistrationDAOImpl;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class RegistrationNewUserService {

	private static final RegistrationNewUserService instance = new RegistrationNewUserService();

	private RegistrationNewUserService() {

	}

	public static RegistrationNewUserService getInstance() {
		return instance;
	}

	public void execute(UserRegistrationInfo userRegistrationInfo) {
		System.out.println("we are in reg service");
		String email = userRegistrationInfo.getUserEmail();
		String login = userRegistrationInfo.getUserLogin();

		if (emailIsUsed(email)) {
			System.out.println("Email занят:(");
		}

		else if (loginIsUsed(login)) {
			System.out.println("Логин занят");
		} else {
			System.out.println("Все в порядке, можно регистрировать");
			System.out.println("Сейчас будем регистрировать");
			RegistrationDAO registrationDAO = RegistrationDAOImpl.getInstance();
			registrationDAO.registrateUser(userRegistrationInfo);
			System.out.println("Оп, зарегистрировали");
		}
		

	}


	private boolean emailIsUsed(String email) {

		InfoCheckerDAO emailChecker = EmailCheckerDAOImlp.getInstance();
		return emailChecker.checkInfoIfExists(email);

	}
	
	private boolean loginIsUsed(String email) {

		InfoCheckerDAO loginChecker = LoginCheckerDAOImpl.getInstance();
		return loginChecker.checkInfoIfExists(email);

	}

}
