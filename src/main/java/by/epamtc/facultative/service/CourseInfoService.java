package by.epamtc.facultative.service;

import java.time.LocalDate;
import java.util.List;

import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;

public class CourseInfoService {

	private static final CourseInfoService instance = new CourseInfoService();

	private CourseInfoService() {

	}

	public static CourseInfoService getInstance() {
		return instance;
	}

	public List<Course> findAvailableCoursesForDepartment(int userDeparttmentId) {

		List<Course> courses = null;
		int departmentId = userDeparttmentId;

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();
		try {
			courses = courseInfoDAOImpl.findCoursesFromDepartment(departmentId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return courses;
	}

	public List<RunnedCourse> findStudentRunCourses(int userId) {

		List<RunnedCourse> courses = null;

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();

		try {
			courses = CourseDAOImpl.getInstance().getRunCoursesOfStudent(userId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countStudentsOnCourse(courses);
		defineCourseLaunchStatus(courses);

		// TODO Auto-generated method stub
		return courses;
	}

	public List<RunnedCourse> findLecturerRunCourses(int userId) {

		List<RunnedCourse> courses = null;

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();

		try {
			courses = courseInfoDAOImpl.getRunCoursesOfLecturer(userId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		countStudentsOnCourse(courses);
		defineCourseLaunchStatus(courses);

		return courses;
	}

	private void countStudentsOnCourse(List<RunnedCourse> courses) {
		int approvedStudentsAmount = 0;
		if (courses != null) {
			for (RunnedCourse course : courses) {

				List<StudentOnCourse> students = course.getStudentsOnCourse();
				if (students != null) {

					for (StudentOnCourse student : students) {

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

	private void countStudentsOnCourse(RunnedCourse course) {
		int approvedStudentsAmount = 0;
		if (course != null) {

			List<StudentOnCourse> students = course.getStudentsOnCourse();
			if (students != null) {
				System.out.println("НЕ НУЛ");
				for (StudentOnCourse student : students) {
					int approvalStatusId = student.getUserApprovalStatusId();
					if (approvalStatusId == 2) {
						approvedStudentsAmount++;
					}
				}
			}

			course.setStudentAmount(approvedStudentsAmount);

		}

	}

	public List<RunnedCourse> findAllAvailableRunCourses() {

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();

		List<RunnedCourse> courses = null;

		try {
			courses = courseInfoDAOImpl.findAllAvailableRunCourses();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countStudentsOnCourse(courses);
		defineCourseLaunchStatus(courses);

		return courses;
	}

	private void defineCourseLaunchStatus(List<RunnedCourse> courses) {

		for (RunnedCourse runnedCourse : courses) {
			int runnedCourseStatusId = runnedCourse.getCourseStatus();
			if (runnedCourseStatusId == 2) {
				runnedCourse.setCourseStatusName("Курс отменён");
			} else if (runnedCourseStatusId == 1) {

				LocalDate today = LocalDate.now();
				LocalDate startDay = runnedCourse.getDateOfStart();
				LocalDate endDate = runnedCourse.getDateOfEnd();

				if (startDay.isAfter(today)) {
					runnedCourse.setCourseStatusName("Идёт набор");
				} else if (endDate.isBefore(today)) {
					runnedCourse.setCourseStatusName("Курс завершён");
				} else {
					runnedCourse.setCourseStatusName("Курс запущен");
				}

			}
		}

	}

	private void defineCourseLaunchStatus(RunnedCourse runnedCourse) {

		int runnedCourseStatusId = runnedCourse.getCourseStatus();
		if (runnedCourseStatusId == 2) {
			runnedCourse.setCourseStatusName("Курс отменён");
		} else if (runnedCourseStatusId == 1) {

			LocalDate today = LocalDate.now();
			LocalDate startDay = runnedCourse.getDateOfStart();
			LocalDate endDate = runnedCourse.getDateOfEnd();

			if (startDay.isAfter(today)) {
				runnedCourse.setCourseStatusName("Идёт набор");
			} else if (endDate.isBefore(today)) {
				runnedCourse.setCourseStatusName("Курс завершён");
			} else {
				runnedCourse.setCourseStatusName("Курс запущен");
			}

		}

	}

	public RunnedCourse findRunCourseById(int runCourseId) {

		RunnedCourse info = null;
		try {
			info = CourseDAOImpl.getInstance().findRunCourse(runCourseId);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countStudentsOnCourse(info);
		defineCourseLaunchStatus(info);
		return info;
	}

	public void applyStudentForRunCourse(int userId, int runCourseId) {

		CourseDAOImpl courseDAOImpl = CourseDAOImpl.getInstance();
		try {
			courseDAOImpl.applyStudentForRunCourse(userId, runCourseId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isStudentOnRunCourse(int userId, RunnedCourse info) {
		List<StudentOnCourse> studentsOnCourse;
		studentsOnCourse = info.getStudentsOnCourse();

		for (StudentOnCourse student : studentsOnCourse) {
			int studentId = student.getUserId();
			if (studentId == userId) {
				return true;
			}
		}
		return false;
	}

	public int getUserOnCourseApprovalStatusId(int userId, RunnedCourse info) {
		
		List<StudentOnCourse> studentsOnCourse;
		studentsOnCourse = info.getStudentsOnCourse();

		for (StudentOnCourse student : studentsOnCourse) {
			int studentId = student.getUserId();
			if (studentId == userId) {
				return student.getUserApprovalStatusId();
			}
		}
		return 0;
	}

	public void findInfoAboutCourse(Course course) {
		
		CourseDAOImpl courseDAO = CourseDAOImpl.getInstance();
		try {
			courseDAO.finsInfoAboutCourse(course);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<RunnedCourse> runnedCourses = course.getRunCourses();
		defineCourseLaunchStatus(runnedCourses);
		countStudentsOnCourse(runnedCourses);
		
		course.setRunCourses(runnedCourses);
		
	}

	public void removeApplicationStudentForRunCourse(int userId, int runCourseId) {
		CourseDAOImpl courseDAOImpl = CourseDAOImpl.getInstance();
		try {
			courseDAOImpl.removeApplicationForRunCourse(userId, runCourseId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
