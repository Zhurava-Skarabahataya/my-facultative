package by.epamtc.facultative.service.impl;

import javax.xml.bind.ValidationException;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.UserDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.UpdateUserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.validator.UserDataValidator;

public class UpdateUserInfoServiceImpl implements UpdateUserInfoService{
	
	private static final UpdateUserInfoServiceImpl instance = new UpdateUserInfoServiceImpl();
	
	private UpdateUserInfoServiceImpl() {
		
	}
	
	public static UpdateUserInfoServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void update(UserInfo userPageInfo) throws ValidationException, ServiceException {
		
		UserDataValidator validator = UserDataValidator.getInstance();
		
		String message = validator.validate(userPageInfo);
		
		if (message != null) {
			throw new ValidationException(message);
		}
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userInfoDAO = daoFactory.getUserDAO();
		
		try {
			userInfoDAO.updateUserInfo(userPageInfo);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
