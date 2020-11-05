package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.RegistrationDAO;
import by.epamtc.facultative.service.PasswordEncryptor;
import by.epamtc.facultative.service.RegistrationService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.validator.UserDataValidator;

public class RegistrationServiceImpl implements RegistrationService{

	private static final RegistrationServiceImpl instance = new RegistrationServiceImpl();
	
	private final String MESSAGE_EMAIL_IS_BUSY = "email_is_busy";
	private final String MESSAGE_LOGIN_IS_BUSY = "login_is_busy";
	

	private RegistrationServiceImpl() {

	}

	public static RegistrationServiceImpl getInstance() {
		return instance;
	}

	@Override
	public String execute(UserRegistrationInfo userInfo) throws ServiceException {
		
		String email;
		String login;
		String unhashedPassword;
		String hashedPassword;

		String validatorMessage;

		email= userInfo.getUserEmail();
		login = userInfo.getUserLogin();
		
		UserDataValidator validator = UserDataValidator.getInstance();
		validatorMessage = validator.validate(userInfo);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		PasswordEncryptor passwordEncryptor = serviceProvider.getPasswordEncryptor();

		unhashedPassword= userInfo.getUserPassword();
		hashedPassword= passwordEncryptor.hashPassword(unhashedPassword);
		
		userInfo.setUserPassword(hashedPassword);

		if (validatorMessage != null){
			return validatorMessage;
		}
		
		if (emailIsUsed(email)) {
			return MESSAGE_EMAIL_IS_BUSY;
		}

		else if (loginIsUsed(login)) {
			return MESSAGE_LOGIN_IS_BUSY;
			
		} else {
			
			DAOFactory daoFactory = DAOFactory.getInstance();
			RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
			
			try {
				registrationDAO.registrateUser(userInfo);
				
			} catch (DAOException e) {

				throw new ServiceException(e);
			}
		}
		return null;
	}


	private boolean emailIsUsed(String email) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
		
		try {
			return registrationDAO.checkEmailIfExists(email);
			
		} catch (DAOException e) {

			throw new ServiceException(e);
		}

	}
	
	private boolean loginIsUsed(String login) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
		
		try {
			return registrationDAO.checkLoginIfExists(login);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

}
