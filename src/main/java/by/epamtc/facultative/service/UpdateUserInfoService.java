package by.epamtc.facultative.service;

import javax.xml.bind.ValidationException;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserDAOImpl;
import by.epamtc.facultative.service.validator.UserDataValidator;
import by.epamtc.facultative.service.validator.Validator;

public class UpdateUserInfoService {
	
	private static final UpdateUserInfoService instance = new UpdateUserInfoService();
	
	private UpdateUserInfoService() {
		
	}
	
	public static UpdateUserInfoService getInstance() {
		return instance;
	}

	public void update(UserInfo userPageInfo) throws ValidationException {
		
		UserDataValidator validator = UserDataValidator.getInstance();
		
		String message = validator.validate(userPageInfo);
		
		if (message != null) {
			
			throw new ValidationException(message);
		}
		
		UserDAOImpl userInfoDAO = UserDAOImpl.getInstance();
		
		try {
			userInfoDAO.updateUserInfo(userPageInfo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
