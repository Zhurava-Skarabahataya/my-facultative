package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserDAOImpl;

import java.util.List;

import by.epamtc.facultative.bean.UserInfo;

public class UserInfoService {

	private static final UserInfoService instance = new UserInfoService();
	
	private final String PHOTO_LINK_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_LINK_POSTFIX = ".jpg";
	

	private UserInfoService() {

	}

	public static UserInfoService getInstance() {
		return instance;
	}
	
	public void execute(UserInfo userInfo) {
		
		UserInfo userPageInfo = userInfo;
		String userLogin = userPageInfo.getUserLogin();
		
		UserDAOImpl userInfoDAOImpl = UserDAOImpl.getInstance();
		try {
			userInfoDAOImpl.provideUserInfo(userPageInfo);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userPageInfo.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
		
		
	}

	

//	public List<UserInfo> findStaffForUser(UserInfo userInfo) {
//		UserDAOImpl userDAO = UserDAOImpl.getInstance();
//		List<UserInfo> staff = null;
//		
//		int userRoleId = userInfo.getUserRoleId();
//		
//		
//		//Для декана
//		if (userRoleId == 3) {
//			
//			int userDepartment = userInfo.getUserFacultyId(); 
//			staff = userDAO.findStaffFromDepartment(userDepartment);
//		}
//		
//		//Для ректора
//		else {
//			staff = userDAO.findStaffFromAllDeratments();
//		}
//		
//		addPhotoLinks(users);
//		
//		return staff;
//	}
//	
	public void addPhotoLinks(List<UserInfo> users) {
		
		for (UserInfo user : users) {
			String userLogin = user.getUserLogin();
			
			user.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
			
		}
	}

	public List<UserInfo> findStaffForUser(UserInfo userInfo) {
		UserDAOImpl userDAO = UserDAOImpl.getInstance();
		
		List<UserInfo> staff = null;
		
		int userRoleId = userInfo.getUserRoleId();
		
		
		//Для декана
		if (userRoleId == 3) {
			
			int userDepartment = userInfo.getUserFacultyId(); 
			try {
				staff = userDAO.findStaffFromDepartment(userDepartment);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Для ректора
		else {
			staff = userDAO.findStaffFromAllDeratments();
		}
		
		addPhotoLinks(staff);
		
		return staff;
	}
	
}
