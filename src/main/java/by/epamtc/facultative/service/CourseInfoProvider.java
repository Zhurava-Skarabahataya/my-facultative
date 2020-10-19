package by.epamtc.facultative.service;

import java.time.LocalDate;
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
		
		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();

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
			}
			else if (runnedCourseStatusId == 1){
				
				LocalDate today = LocalDate.now();
				LocalDate startDay = runnedCourse.getDateOfStart();
				LocalDate endDate = runnedCourse.getDateOfEnd();
				
				if (startDay.isAfter(today)) {
					runnedCourse.setCourseStatusName("Идёт набор");
				}
				else if (endDate.isBefore(today)) {
					runnedCourse.setCourseStatusName("Курс завершён");
				}
				else {
					runnedCourse.setCourseStatusName("Курс запущен");
				}
				
			}
		}
		
	}
	
private void defineCourseLaunchStatus(InfoAboutRunnedCourse runnedCourse) {
				
			int runnedCourseStatusId = runnedCourse.getCourseStatus();
			if (runnedCourseStatusId == 2) {
				runnedCourse.setCourseStatusName("Курс отменён");
			}
			else if (runnedCourseStatusId == 1){
				
				LocalDate today = LocalDate.now();
				LocalDate startDay = runnedCourse.getDateOfStart();
				LocalDate endDate = runnedCourse.getDateOfEnd();
				
				if (startDay.isAfter(today)) {
					runnedCourse.setCourseStatusName("Идёт набор");
				}
				else if (endDate.isBefore(today)) {
					runnedCourse.setCourseStatusName("Курс завершён");
				}
				else {
					runnedCourse.setCourseStatusName("Курс запущен");
				}
				
			}
		
		
	}

	public InfoAboutRunnedCourse findRunCourseById(int runCourseId) {
		
		InfoAboutRunnedCourse info  = null;
		try {
			info = CourseInfoDAOImpl.getInstance().findRunCourse(runCourseId);
			System.out.println(info);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countStudentsOnCourse(info);
		defineCourseLaunchStatus(info);
		return info;
	}
	
	

}
