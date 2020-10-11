package by.epamtc.facultative.service;

import by.epamtc.facultative.DAO.impl.UserInfoDAOImpl;
import by.epamtc.facultative.bean.UserPageInfo;

public class UserPageInfoProviderService {

	private static final UserPageInfoProviderService instance = new UserPageInfoProviderService();

	private UserPageInfoProviderService() {

	}

	public static UserPageInfoProviderService getInstance() {
		return instance;
	}
	
	public void execute(UserPageInfo userInfo) {
		
		UserPageInfo userPageInfo = userInfo;
		String userLogin = userPageInfo.getUserLogin();

		UserInfoDAOImpl userInfoDAOImpl = UserInfoDAOImpl.getInstance();
		userInfoDAOImpl.provideUserInfo(userPageInfo);
		
		userPageInfo.setUserPhotoLink("user_photos/" + userLogin + ".jpg");
		System.out.println("user_photos/" + userLogin + ".jpg");
		
		
	}
	
}
