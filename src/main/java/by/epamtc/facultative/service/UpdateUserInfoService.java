package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserInfoDAOImpl;

public class UpdateUserInfoService {
	
	private static final UpdateUserInfoService instance = new UpdateUserInfoService();
	
	private UpdateUserInfoService() {
		
	}
	
	public static UpdateUserInfoService getInstance() {
		return instance;
	}

	public void update(UserInfo userPageInfo) {
		
		UserInfoDAOImpl userInfoDAO = UserInfoDAOImpl.getInstance();
		try {
			userInfoDAO.updateUserInfo(userPageInfo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
