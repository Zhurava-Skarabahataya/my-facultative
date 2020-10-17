package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.CourseStudentInfo;
import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseInfoDAOImpl;

public class CourseInfoProvider {

	private static final CourseInfoProvider instance = new CourseInfoProvider();

	private CourseInfoProvider() {

	}

	public static CourseInfoProvider getInstance() {
		return instance;
	}

	public List<InfoAboutCourse> findAvailableCoursesForDepartment(int userDeparttmentId) {

		List<InfoAboutCourse> courses = null;
		int departmentId = userDeparttmentId;

		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();
		try {
			courses = courseInfoDAOImpl.findCoursesFromDepartment(departmentId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return courses;
	}

	public List<InfoAboutRunnedCourse> findStudentRunCourses(int userId) {

		List<InfoAboutRunnedCourse> courses = null;

		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();

		try {
			courses = CourseInfoDAOImpl.getInstance().getRunCoursesOfStudent(userId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	public List<InfoAboutRunnedCourse> findLecturerRunCourses(int userId) {

		List<InfoAboutRunnedCourse> courses = null;

		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();

		try {
			courses = courseInfoDAOImpl.getRunCoursesOfLecturer(userId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		countStudentsOnCourse(courses);

		return courses;
	}

	private void countStudentsOnCourse(List<InfoAboutRunnedCourse> courses) {
		int approvedStudentsAmount = 0;
		if (courses != null) {
			for (InfoAboutRunnedCourse course : courses) {

				List<CourseStudentInfo> students = course.getStudentsOnCourse();
				if (students != null) {
					System.out.println("НЕ НУЛ");
					for (CourseStudentInfo student : students) {
						int approvalStatusId = student.getUserApprovalStatusId();
						if (approvalStatusId == 2) {
							approvedStudentsAmount++;
						}
					}
				}

				course.setStudentAmount(approvedStudentsAmount);
			}
		}

	}

}
