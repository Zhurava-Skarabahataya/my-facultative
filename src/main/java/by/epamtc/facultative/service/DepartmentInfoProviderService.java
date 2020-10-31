package by.epamtc.facultative.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.DepartmentDAOImpl;

public class DepartmentInfoProviderService {

	private static final DepartmentInfoProviderService instance = new DepartmentInfoProviderService();

	private final String PHOTO_PATH_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_PATH_POSTFIX = ".jpg";
	
		
	private static final String UNIVERSITY_PROPERTIES_FILE = "university";
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(UNIVERSITY_PROPERTIES_FILE);
	


	private static final String AMOUNT_OF_DEPARTMENTS = "university.amount_of_departments";

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

		createUsersPhotoPath(lecturers);

		department.setLecturers(lecturers);
		department.setCourses(courses);
	}

	public void createUsersPhotoPath(List<UserInfo> users) {
		
		for (UserInfo user : users) {
			String userLogin = user.getUserLogin();
			StringBuilder path = new StringBuilder();
			path.append(PHOTO_PATH_PREFIX);
			path.append(userLogin);
			path.append(PHOTO_PATH_POSTFIX);
			
			user.setUserPhotoLink(path.toString());
		}
		
	}

	public List<UserInfo> findStudentsOfDepartment(int departmentId) {
		
		DepartmentDAOImpl departmentDAOImpl = DepartmentDAOImpl.getInstance();

		List<UserInfo> students = null;
		try {
			students = departmentDAOImpl.findStudentsInDepartment(departmentId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createUsersPhotoPath(students);
		
		return students;
	}

	public List<Department> findStudentsOfAllDepartments() {
		
		DepartmentDAOImpl departmentDAOImpl = DepartmentDAOImpl.getInstance();
		
		List<Department> studentsDividedByDepartments = new ArrayList<Department>();

		List<UserInfo> studentsInAllDepartments = null;
		
		try {
			studentsInAllDepartments = departmentDAOImpl.findStudentsInAllDepartments();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int amountOfDepartments = Integer.parseInt(resourceBundle.getString(AMOUNT_OF_DEPARTMENTS));

		for (int departmentCount = 0; departmentCount < amountOfDepartments; departmentCount++) {
				
			int departmentId = departmentCount + 1;

			Department department = new Department();
			department.setDepartmentID(departmentId);
			
			
			
			List <UserInfo> students = new ArrayList<UserInfo>();
			
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

}
