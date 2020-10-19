package by.epamtc.facultative.service;

import java.time.LocalDate;
import java.util.List;

import by.epamtc.facultative.bean.CourseStudentInfo;
import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;

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

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();
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

	public List<InfoAboutRunnedCourse> findLecturerRunCourses(int userId) {

		List<InfoAboutRunnedCourse> courses = null;

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

	private void countStudentsOnCourse(List<InfoAboutRunnedCourse> courses) {
		int approvedStudentsAmount = 0;
		if (courses != null) {
			for (InfoAboutRunnedCourse course : courses) {

				List<CourseStudentInfo> students = course.getStudentsOnCourse();
				if (students != null) {

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

	private void countStudentsOnCourse(InfoAboutRunnedCourse course) {
		int approvedStudentsAmount = 0;
		if (course != null) {

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

	public List<InfoAboutRunnedCourse> findAllAvailableRunCourses() {

		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();

		List<InfoAboutRunnedCourse> courses = null;

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

	private void defineCourseLaunchStatus(List<InfoAboutRunnedCourse> courses) {

		for (InfoAboutRunnedCourse runnedCourse : courses) {
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

	private void defineCourseLaunchStatus(InfoAboutRunnedCourse runnedCourse) {

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

	public InfoAboutRunnedCourse findRunCourseById(int runCourseId) {

		InfoAboutRunnedCourse info = null;
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

	public boolean isStudentOnRunCourse(int userId, InfoAboutRunnedCourse info) {
		List<CourseStudentInfo> studentsOnCourse;
		studentsOnCourse = info.getStudentsOnCourse();

		for (CourseStudentInfo student : studentsOnCourse) {
			int studentId = student.getUserId();
			if (studentId == userId) {
				return true;
			}
		}
		return false;
	}

	public int getUserOnCourseApprovalStatusId(int userId, InfoAboutRunnedCourse info) {
		
		List<CourseStudentInfo> studentsOnCourse;
		studentsOnCourse = info.getStudentsOnCourse();

		for (CourseStudentInfo student : studentsOnCourse) {
			int studentId = student.getUserId();
			if (studentId == userId) {
				return student.getUserApprovalStatusId();
			}
		}
		return 0;
	}

}
