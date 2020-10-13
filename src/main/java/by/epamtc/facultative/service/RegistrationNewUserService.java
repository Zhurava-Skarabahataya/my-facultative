package by.epamtc.facultative.service;

import by.epamtc.facultative.DAO.InfoCheckerDAO;
import by.epamtc.facultative.DAO.RegistrationDAO;
import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.EmailCheckerDAOImlp;
import by.epamtc.facultative.DAO.impl.LoginCheckerDAOImpl;
import by.epamtc.facultative.DAO.impl.RegistrationDAOImpl;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.service.validator.UserDataValidator;

public class RegistrationNewUserService {

	private static final RegistrationNewUserService instance = new RegistrationNewUserService();

	private RegistrationNewUserService() {

	}

	public static RegistrationNewUserService getInstance() {
		return instance;
	}

	public void execute(UserRegistrationInfo userRegistrationInfo) {
		String email = userRegistrationInfo.getUserEmail();
		String login = userRegistrationInfo.getUserLogin();

		//RegistrationValidator rv = UserDataValidator.getInstance();
		
		
		
		if (emailIsUsed(email)) {
			System.out.println("Email занят:(");
		}

		else if (loginIsUsed(login)) {
			System.out.println("Логин занят");
		} else {
			System.out.println("Все в порядке, можно регистрировать");
			System.out.println("Сейчас будем регистрировать");
			RegistrationDAO registrationDAO = RegistrationDAOImpl.getInstance();
			try {
				registrationDAO.registrateUser(userRegistrationInfo);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Оп, зарегистрировали");
		}
		

	}


	private boolean emailIsUsed(String email) {

		InfoCheckerDAO emailChecker = EmailCheckerDAOImlp.getInstance();
		try {
			return emailChecker.checkInfoIfExists(email);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	private boolean loginIsUsed(String email) {

		InfoCheckerDAO loginChecker = LoginCheckerDAOImpl.getInstance();
		try {
			return loginChecker.checkInfoIfExists(email);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

}
