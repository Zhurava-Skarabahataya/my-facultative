package by.epamtc.facultative.service;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.DepartmentDAOImpl;

public class DepartmentInfoProviderService {

	private static final DepartmentInfoProviderService instance = new DepartmentInfoProviderService();

	private final String PHOTO_PATH_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_PATH_POSTFIX = ".jpg";

	private DepartmentInfoProviderService() {

	}

	public static DepartmentInfoProviderService getInstance() {
		return instance;
	}

	public List<Department> findAllDepartmentsInfo() {

		List<Department> departments = null;

		DepartmentDAOImpl departmentDAOImpl = DepartmentDAOImpl.getInstance();
		try {
			departments = departmentDAOImpl.findAllDepartments();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setDeanPhotoPath(departments);

		return departments;
	}

	public void setDeanPhotoPath(List<Department> departments) {

		for (Department department : departments) {
			String deanLogin = department.getDeanLogin();
			StringBuilder photoPath = new StringBuilder();
			photoPath.append(PHOTO_PATH_PREFIX);
			photoPath.append(deanLogin);
			photoPath.append(PHOTO_PATH_POSTFIX);
			department.setDeanImagePath(photoPath.toString());
		}

	}

//	public List<InfoAboutCourse> findAllCoursesInDepartments() {
//		List<InfoAboutCourse> courses;
//
//		courses = null;
//		DepartmentDAOImpl departmentDAOImpl = DepartmentDAOImpl.getInstance();
//		courses = departmentDAOImpl.findAllCoursesInDepartment();
//
//		return courses;
//	}

	public void findLecturersAndCoursesForDepartment(Department department) {

		DepartmentDAOImpl departmentDAOImpl = DepartmentDAOImpl.getInstance();
		int departmentId = department.getDepartmentID();
		List<UserInfo> lecturers = null;
		List<Course> courses = null;

		try {
			lecturers = departmentDAOImpl.findLecturersInDepartment(departmentId);
			courses = departmentDAOImpl.findCoursesInDepartment(departmentId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		createLecturerPhotoPath(lecturers);

		department.setLecturers(lecturers);
		department.setCourses(courses);
	}

	public void createLecturerPhotoPath(List<UserInfo> lecturers) {
		
		for (UserInfo lecturer : lecturers) {
			String userLogin = lecturer.getUserLogin();
			StringBuilder path = new StringBuilder();
			path.append(PHOTO_PATH_PREFIX);
			path.append(userLogin);
			path.append(PHOTO_PATH_POSTFIX);
			
			lecturer.setUserPhotoLink(path.toString());
		}
		
	}

}
