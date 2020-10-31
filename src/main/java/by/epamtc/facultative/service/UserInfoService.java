package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserDAOImpl;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.UserInfo;

public class UserInfoService {

	private static final UserInfoService instance = new UserInfoService();
	
	private static final Logger logger = Logger.getLogger(UserInfoService.class);

	private final String PHOTO_LINK_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_LINK_POSTFIX = ".jpg";
		
	private static final String UNIVERSITY_PROPERTIES_FILE = "university";
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(UNIVERSITY_PROPERTIES_FILE);
	


	private static final String AMOUNT_OF_DEPARTMENTS = "university.amount_of_departments";

	private UserInfoService() {

	}

	public static UserInfoService getInstance() {
		return instance;
	}
	
	public void findUserInfo(UserInfo userInfo) {
		
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

	
	public void addPhotoLinks(List<UserInfo> users) {
		
		for (UserInfo user : users) {
			String userLogin = user.getUserLogin();
			
			user.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
			
		}
	}

	
	public DepartmentStaff findFacultyStaffForDean(UserInfo userInfo) {
		
		UserDAOImpl userDAO = UserDAOImpl.getInstance();
		
		int departmentId = userInfo.getUserFacultyId();
		
		DepartmentStaff facultyStaffInfo = userDAO.findFacultyStaffInfo(departmentId);

		addPhotoLinks(facultyStaffInfo.getWorkingStaff());
		addPhotoLinks(facultyStaffInfo.getNotApprovedStaff());
		addPhotoLinks(facultyStaffInfo.getFiredStaff());
		
		return facultyStaffInfo;
	}

	public List<DepartmentStaff> findUnivercityStaffForRector(UserInfo userInfo) {

		UserDAOImpl userDAO = UserDAOImpl.getInstance();
		
		DepartmentStaff facultiesStaffInfo = userDAO.findAllFacultiesStaffInfo();
		
		List <DepartmentStaff> staffDividedByDepartments = divideDifferentDepartmentsStaff(facultiesStaffInfo);
		
		
		return staffDividedByDepartments;
	}

	private List<DepartmentStaff> divideDifferentDepartmentsStaff(DepartmentStaff facultiesStaffInfo) {
		
		List<DepartmentStaff> staffDividedByDepartments = new ArrayList<DepartmentStaff>();
		
		int amountOfDepartments = Integer.parseInt(resourceBundle.getString(AMOUNT_OF_DEPARTMENTS));
		
		for (int departmentCount = 0; departmentCount < amountOfDepartments; departmentCount++) {
			
			int departmentId = departmentCount + 1;
			
			DepartmentStaff departmentStaff = new DepartmentStaff();
			
			departmentStaff.setDepartmentId(departmentId);
			
			List <UserInfo> workingLecturers = new ArrayList<UserInfo>();
			List <UserInfo> notApprovedLecturers = new ArrayList<UserInfo>();
			List <UserInfo> firedLecturers = new ArrayList<UserInfo>();
			
			for (UserInfo employee : facultiesStaffInfo.getAllStaff()) {
				
				int employeeDepartmentId = employee.getUserFacultyId();
	

				if (employeeDepartmentId == departmentId) {
					
					int employeeStatus = employee.getUserStatusId();
					String employeeDepartmentName = employee.getUserFaculty();
					
					departmentStaff.setDepartmentName(employeeDepartmentName);
					
					if (employeeStatus == 2) {
						workingLecturers.add(employee);
					}
					else if (employeeStatus == 1) {
						notApprovedLecturers.add(employee);					}
					
					}
				else {
					firedLecturers.add(employee);
				}
				
			}
			
			addPhotoLinks(workingLecturers);
			addPhotoLinks(notApprovedLecturers);
			addPhotoLinks(firedLecturers);
			
			departmentStaff.setWorkingStaff(workingLecturers);
			departmentStaff.setNotApprovedStaff(notApprovedLecturers);
			departmentStaff.setFiredStaff(firedLecturers);
						
			staffDividedByDepartments.add(departmentStaff);
			
		}
		
		
		return staffDividedByDepartments;
	}

	public UserInfo findUserInfo(int userId) {
		
		UserDAOImpl userInfoDAOImpl = UserDAOImpl.getInstance();

		
		UserInfo userInfo = null;
		try {
			userInfo = userInfoDAOImpl.findUserInfoById(userId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String userLogin = userInfo.getUserLogin();
		
		userInfo.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
		
		return userInfo;
	}
	
}
