package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserInfoDAOImpl;
import by.epamtc.facultative.bean.UserInfo;

public class UserPageInfoProviderService {

	private static final UserPageInfoProviderService instance = new UserPageInfoProviderService();

	private UserPageInfoProviderService() {

	}

	public static UserPageInfoProviderService getInstance() {
		return instance;
	}
	
	public void execute(UserInfo userInfo) {
		
		UserInfo userPageInfo = userInfo;
		String userLogin = userPageInfo.getUserLogin();
		
		UserInfoDAOImpl userInfoDAOImpl = UserInfoDAOImpl.getInstance();
		try {
			userInfoDAOImpl.provideUserInfo(userPageInfo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userPageInfo.setUserPhotoLink("D:/Java/facultative-project/user_photos/" + userLogin + ".jpg");
		
		
	}
	
}
