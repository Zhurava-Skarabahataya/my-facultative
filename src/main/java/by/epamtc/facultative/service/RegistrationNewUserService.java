package by.epamtc.facultative.service;

import org.apache.log4j.Logger;

import by.epamtc.facultative.DAO.InfoCheckerDAO;
import by.epamtc.facultative.DAO.RegistrationDAO;
import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.EmailCheckerDAOImlp;
import by.epamtc.facultative.DAO.impl.LoginCheckerDAOImpl;
import by.epamtc.facultative.DAO.impl.RegistrationDAOImpl;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.validator.UserDataValidator;

public class RegistrationNewUserService {

	private static final RegistrationNewUserService instance = new RegistrationNewUserService();
	
	private static final Logger logger = Logger.getLogger(RegistrationNewUserService.class);

	private RegistrationNewUserService() {

	}

	public static RegistrationNewUserService getInstance() {
		return instance;
	}

	public String execute(UserRegistrationInfo userRegistrationInfo) throws ServiceException {
		
		String email = userRegistrationInfo.getUserEmail();
		String login = userRegistrationInfo.getUserLogin();
		

		UserDataValidator validator = UserDataValidator.getInstance();
		String validatorMessage = validator.validate(userRegistrationInfo);

		String unhashedPassword = userRegistrationInfo.getUserPassword();
		String hashedPassword = PasswordEncryptor.getInstance().hashPassword(unhashedPassword);
		
		userRegistrationInfo.setUserPassword(hashedPassword);

		if (validatorMessage != null){
			return validatorMessage;
		}
		
		
		if (emailIsUsed(email)) {
			return "Email занят:(";
		}

		else if (loginIsUsed(login)) {
			return "Логин занят";
			
		} else {
			RegistrationDAO registrationDAO = RegistrationDAOImpl.getInstance();
			
			try {
				registrationDAO.registrateUser(userRegistrationInfo);
				
			} catch (DAOException e) {
				logger.error(e);
				throw new ServiceException(e);
			}
		}
		return null;
		

	}


	private boolean emailIsUsed(String email) throws ServiceException {

		InfoCheckerDAO emailChecker = EmailCheckerDAOImlp.getInstance();
		try {
			return emailChecker.checkInfoIfExists(email);
			
		} catch (DAOException e) {

			throw new ServiceException(e);
		}

	}
	
	private boolean loginIsUsed(String email) throws ServiceException {

		InfoCheckerDAO loginChecker = LoginCheckerDAOImpl.getInstance();
		try {
			return loginChecker.checkInfoIfExists(email);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

}
