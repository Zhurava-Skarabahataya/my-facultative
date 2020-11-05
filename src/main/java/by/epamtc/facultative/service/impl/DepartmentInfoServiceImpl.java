package by.epamtc.facultative.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.DepartmentDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.DepartmentInfoService;
import by.epamtc.facultative.service.RatingService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class DepartmentInfoServiceImpl implements DepartmentInfoService {

	private static final DepartmentInfoServiceImpl instance = new DepartmentInfoServiceImpl();

	private static final String UNIVERSITY_PROPERTIES_FILE = "university";
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(UNIVERSITY_PROPERTIES_FILE);
	private static final String AMOUNT_OF_DEPARTMENTS = "university.amount_of_departments";

	private final String PHOTO_PATH_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_PATH_POSTFIX = ".jpg";

	private DepartmentInfoServiceImpl() {

	}

	public static DepartmentInfoServiceImpl getInstance() {
		return instance;
	}

	@Override
	public List<Department> findAllDepartmentsInfo() throws ServiceException {

		List<Department> departments;
		departments = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

		try {
			departments = departmentDAO.findAllDepartments();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		setDeanPhotoPath(departments);

		return departments;
	}

	@Override
	public void findLecturersAndCoursesForDepartment(Department department) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

		List<UserInfo> lecturers;
		List<Course> courses;
		int departmentId;

		lecturers = null;
		courses = null;
		departmentId = department.getDepartmentID();

		try {
			lecturers = departmentDAO.findLecturersInDepartment(departmentId);
			courses = departmentDAO.findCoursesInDepartment(departmentId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		createUsersPhotoPath(lecturers);

		department.setLecturers(lecturers);
		department.setCourses(courses);
	}

	@Override
	public List<UserInfo> findStudentsOfDepartment(int departmentId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

		List<UserInfo> students;
		students = null;
		try {
			students = departmentDAO.findStudentsInDepartment(departmentId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		RatingService ratingService = serviceProvider.getRatingService();

		ratingService.countRatingForStudents(students);

		createUsersPhotoPath(students);

		return students;
	}

	@Override
	public List<Department> findStudentsOfAllDepartments() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

		List<Department> studentsDividedByDepartments;
		List<UserInfo> studentsInAllDepartments;

		studentsInAllDepartments = null;
		studentsDividedByDepartments = new ArrayList<Department>();

		try {
			studentsInAllDepartments = departmentDAO.findStudentsInAllDepartments();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		RatingService ratingService = serviceProvider.getRatingService();

		ratingService.countRatingForStudents(studentsInAllDepartments);

		studentsDividedByDepartments = divideStudentsToDepartments(studentsInAllDepartments);

		return studentsDividedByDepartments;
	}

	private List<Department> divideStudentsToDepartments(List<UserInfo> studentsInAllDepartments) {

		List<Department> studentsDividedByDepartments;
		int amountOfDepartments;

		studentsDividedByDepartments = new ArrayList<Department>();

		amountOfDepartments = Integer.parseInt(resourceBundle.getString(AMOUNT_OF_DEPARTMENTS));

		for (int departmentCount = 0; departmentCount < amountOfDepartments; departmentCount++) {

			int departmentId = departmentCount + 1;

			Department department = new Department();
			department.setDepartmentID(departmentId);

			List<UserInfo> students = new ArrayList<UserInfo>();

			for (UserInfo student : studentsInAllDepartments) {

				int studentDepartmentId = student.getUserFacultyId();

				if (studentDepartmentId == departmentId) {

					department.setDepartmentName(student.getUserFaculty());

					students.add(student);
					department.setStudents(students);

				}
			}
			createUsersPhotoPath(students);
			studentsDividedByDepartments.add(department);
		}

		return studentsDividedByDepartments;
	}

	private void setDeanPhotoPath(List<Department> departments) {

		for (Department department : departments) {

			String deanLogin;
			StringBuilder photoPath;

			deanLogin = department.getDeanLogin();

			photoPath = new StringBuilder();

			photoPath.append(PHOTO_PATH_PREFIX);
			photoPath.append(deanLogin);
			photoPath.append(PHOTO_PATH_POSTFIX);

			department.setDeanImagePath(photoPath.toString());
		}
	}

	private void createUsersPhotoPath(List<UserInfo> users) {

		for (UserInfo user : users) {

			String userLogin;
			StringBuilder path;

			userLogin = user.getUserLogin();
			path = new StringBuilder();

			path.append(PHOTO_PATH_PREFIX);
			path.append(userLogin);
			path.append(PHOTO_PATH_POSTFIX);

			user.setUserPhotoLink(path.toString());
		}
	}
}
