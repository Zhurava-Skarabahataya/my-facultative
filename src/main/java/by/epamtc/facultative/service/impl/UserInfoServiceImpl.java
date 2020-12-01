package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.UserDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.RatingService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;

public class UserInfoServiceImpl implements UserInfoService {

	private static final UserInfoServiceImpl instance = new UserInfoServiceImpl();

	//private final String PHOTO_LINK_PREFIX = "D:/Java/JavaWorkspace/facultative-project/src/main/webapp/image";
	private final String PHOTO_LINK_PREFIX = "image/";
	private final String PHOTO_LINK_POSTFIX = ".jpg";
	private final int LECTURER_STATUS_WORKING = 2;
	private final int LECTURER_STATUS_NOT_APPROVED = 1;

	private static final String UNIVERSITY_PROPERTIES_FILE = "university";
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(UNIVERSITY_PROPERTIES_FILE);

	private static final String AMOUNT_OF_DEPARTMENTS = "university.amount_of_departments";

	private UserInfoServiceImpl() {

	}

	public static UserInfoServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void findUserInfo(UserInfo userInfo) throws ServiceException {

		String userLogin = userInfo.getUserLogin();

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			userDAO.provideUserInfo(userInfo);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		userInfo.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
	}

	@Override
	public void findUserInfoById(UserInfo userInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			userDAO.provideUserInfoById(userInfo);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		String userLogin = userInfo.getUserLogin();
		userInfo.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);

	}

	@Override
	public DepartmentStaff findFacultyStaffForDean(UserInfo userInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		int departmentId;
		DepartmentStaff facultyStaffInfo;

		departmentId = userInfo.getUserFacultyId();

		try {
			facultyStaffInfo = userDAO.findFacultyStaffInfo(departmentId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		addPhotoLinks(facultyStaffInfo.getWorkingStaff());
		addPhotoLinks(facultyStaffInfo.getNotApprovedStaff());
		addPhotoLinks(facultyStaffInfo.getFiredStaff());

		return facultyStaffInfo;
	}

	@Override
	public List<DepartmentStaff> findUnivercityStaffForRector(UserInfo userInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		DepartmentStaff facultiesStaffInfo;
		List<DepartmentStaff> staffDividedByDepartments;

		try {
			facultiesStaffInfo = userDAO.findAllFacultiesStaffInfo();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		staffDividedByDepartments = divideDifferentDepartmentsStaff(facultiesStaffInfo);

		return staffDividedByDepartments;
	}

	@Override
	public void findUserRating(UserInfo loggedUserInfo) throws ServiceException {

		int studentId;
		String studentLogin;

		studentId = loggedUserInfo.getUserId();
		studentLogin = loggedUserInfo.getUserLogin();

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		List<Mark> marks;
		try {

			if (studentLogin != null) {
				marks = userDAO.findStudentResults(studentLogin);
			} else {
				marks = userDAO.findStudentResults(studentId);
			}
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		loggedUserInfo.setStudentMarks(marks);

		RatingService ratingService = ServiceProvider.getInstance().getRatingService();
		ratingService.countRatingForStudent(loggedUserInfo);

	}

	private void addPhotoLinks(List<UserInfo> users) {

		for (UserInfo user : users) {

			String userLogin = user.getUserLogin();

			user.setUserPhotoLink(PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX);
		}
	}

	private List<DepartmentStaff> divideDifferentDepartmentsStaff(DepartmentStaff facultiesStaffInfo) {

		List<DepartmentStaff> staffDividedByDepartments;
		int amountOfDepartments;

		staffDividedByDepartments = new ArrayList<DepartmentStaff>();

		amountOfDepartments = Integer.parseInt(resourceBundle.getString(AMOUNT_OF_DEPARTMENTS));

		for (int departmentCount = 0; departmentCount < amountOfDepartments; departmentCount++) {

			int departmentId = departmentCount + 1;

			DepartmentStaff departmentStaff = new DepartmentStaff();

			List<UserInfo> workingLecturers = new ArrayList<UserInfo>();
			List<UserInfo> notApprovedLecturers = new ArrayList<UserInfo>();
			List<UserInfo> firedLecturers = new ArrayList<UserInfo>();

			departmentStaff.setDepartmentId(departmentId);

			for (UserInfo employee : facultiesStaffInfo.getAllStaff()) {

				int employeeDepartmentId = employee.getUserFacultyId();

				if (employeeDepartmentId == departmentId) {

					int employeeStatus = employee.getUserStatusId();
					String employeeDepartmentName = employee.getUserFaculty();

					departmentStaff.setDepartmentName(employeeDepartmentName);
					
					System.out.println(employee);

					if (employeeStatus == LECTURER_STATUS_WORKING) {
						workingLecturers.add(employee);
					} else if (employeeStatus == LECTURER_STATUS_NOT_APPROVED) {
						notApprovedLecturers.add(employee);
					}
				} else {
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

}
