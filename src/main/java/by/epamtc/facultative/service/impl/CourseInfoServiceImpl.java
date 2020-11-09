package by.epamtc.facultative.service.impl;

import java.time.LocalDate;
import java.util.List;

import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.dao.CourseDAO;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class CourseInfoServiceImpl implements CourseInfoService {

	private static final CourseInfoServiceImpl instance = new CourseInfoServiceImpl();

	//private final String PHOTO_LINK_PREFIX = "D:/Java/facultative-project/user_photos/";
	private final String PHOTO_LINK_PREFIX = "image/";
	private final String PHOTO_LINK_POSTFIX = ".jpg";
	private final int APPROVED_STUDENT_STATUS = 2;
	private final int RUN_COURSE_STATUS_CANSELLED = 2;
	private final int RUN_COURSE_STATUS_RECRUTING = 1;
	private final int RUN_COURSE_STATE_CANSELLED = 1;
	private final int RUN_COURSE_STATE_RECRUITING = 3;
	private final int RUN_COURSE_STATE_ENDED = 2;
	private final int RUN_COURSE_STATE_RUNNING = 4;

	private CourseInfoServiceImpl() {

	}

	public static CourseInfoServiceImpl getInstance() {
		return instance;
	}

	@Override
	public List<Course> findAvailableCoursesForDepartment(int userDeparttmentId) throws ServiceException {

		List<Course> courses;
		int departmentId;

		courses = null;
		departmentId = userDeparttmentId;

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseInfoDAO = daoFactory.getCourseDAO();

		try {
			courses = courseInfoDAO.findCoursesFromDepartment(departmentId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return courses;
	}

	@Override
	public void findStudentRunCourses(UserInfo userPageInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseInfoDAO = daoFactory.getCourseDAO();

		try {
			courseInfoDAO.getRunCoursesOfStudent(userPageInfo);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		countStudentsOnCourse(userPageInfo.getCanselledCourses());
		countStudentsOnCourse(userPageInfo.getEndedCourses());
		countStudentsOnCourse(userPageInfo.getCurrentCourses());

	}

	@Override
	public void findLecturerRunCourses(UserInfo userPageInfo) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.getRunCoursesOfLecturer(userPageInfo);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}

		countStudentsOnCourse(userPageInfo.getCanselledCourses());
		countStudentsOnCourse(userPageInfo.getEndedCourses());
		countStudentsOnCourse(userPageInfo.getCurrentCourses());
	}
	
	@Override
	public void findDeanRunCourses(UserInfo userPageInfo){
		
	}

	@Override
	public List<RunnedCourse> findAllAvailableRunCourses() throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		List<RunnedCourse> courses = null;

		try {
			courses = courseDAO.findAllAvailableRunCourses();

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		countStudentsOnCourse(courses);
		defineCourseLaunchStatus(courses);

		return courses;
	}

	@Override
	public RunnedCourse findRunCourseById(int runCourseId) throws ServiceException {

		RunnedCourse info = null;

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {

			info = courseDAO.findRunCourse(runCourseId);

			String lecturerLogin;
			String lecturerPhotoLink;

			lecturerLogin = info.getLecturerLogin();
			lecturerPhotoLink = PHOTO_LINK_PREFIX + lecturerLogin + PHOTO_LINK_POSTFIX;

			info.setLecturerPhotoLink(lecturerPhotoLink);

			List<StudentOnCourse> students;
			students = info.getStudentsOnCourse();

			addPhotoLinks(students);

			countStudentsOnCourse(info);
			defineCourseLaunchStatus(info);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return info;
	}

	private void addPhotoLinks(List<StudentOnCourse> users) {

		for (StudentOnCourse user : users) {

			String userLogin;
			String userPhotoLink;

			userLogin = user.getUserLogin();
			userPhotoLink = PHOTO_LINK_PREFIX + userLogin + PHOTO_LINK_POSTFIX;

			user.setUserPhotoLink(userPhotoLink);

		}
	}

	@Override
	public void applyStudentForRunCourse(int userId, int runCourseId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.applyStudentForRunCourse(userId, runCourseId);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void findInfoAboutCourse(Course course) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.findInfoAboutCourse(course);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		List<RunnedCourse> runnedCourses;
		runnedCourses = course.getRunCourses();

		defineCourseLaunchStatus(runnedCourses);
		countStudentsOnCourse(runnedCourses);

		course.setRunCourses(runnedCourses);
	}

	@Override
	public void removeApplicationStudentForRunCourse(int userId, int runCourseId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.removeApplicationForRunCourse(userId, runCourseId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.giveStudentGraveOnRunCourse(studentId, runCourseId, grade);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public int getUserMarkOnCourse(int userId, RunnedCourse runCourse) {

		List<StudentOnCourse> studentsOnCourse;
		studentsOnCourse = runCourse.getStudentsOnCourse();

		for (StudentOnCourse student : studentsOnCourse) {

			int studentId;
			studentId = student.getUserId();

			if (studentId == userId) {
				return student.getResult();
			}
		}
		return 0;
	}

	private void countStudentsOnCourse(List<RunnedCourse> courses) {

		if (courses != null) {
			for (RunnedCourse course : courses) {

				int approvedStudentsAmount;
				approvedStudentsAmount = 0;

				List<StudentOnCourse> students;
				students = course.getStudentsOnCourse();

				if (students != null) {

					for (StudentOnCourse student : students) {

						int approvalStatusId = student.getUserApprovalStatusId();

						if (approvalStatusId == APPROVED_STUDENT_STATUS) {
							approvedStudentsAmount++;
						}
					}
				}
				course.setStudentAmount(approvedStudentsAmount);
			}
		}
	}

	private void countStudentsOnCourse(RunnedCourse course) {

		int approvedStudentsAmount;
		approvedStudentsAmount = 0;

		if (course != null) {

			List<StudentOnCourse> students = course.getStudentsOnCourse();

			if (students != null) {

				for (StudentOnCourse student : students) {

					int approvalStatusId = student.getUserApprovalStatusId();
					if (approvalStatusId == APPROVED_STUDENT_STATUS) {
						approvedStudentsAmount++;
					}
				}
			}
			course.setStudentAmount(approvedStudentsAmount);
		}
	}

	@Override
	public void defineCourseLaunchStatus(List<RunnedCourse> courses) {

		for (RunnedCourse runnedCourse : courses) {

			int runnedCourseStatusId;
			runnedCourseStatusId = runnedCourse.getCourseStatus();

			if (runnedCourseStatusId == RUN_COURSE_STATUS_CANSELLED) {

				runnedCourse.setCurrentState(RUN_COURSE_STATE_CANSELLED);

			} else if (runnedCourseStatusId == RUN_COURSE_STATUS_RECRUTING) {

				LocalDate today = LocalDate.now();

				LocalDate startDay = runnedCourse.getDateOfStart();
				LocalDate endDate = runnedCourse.getDateOfEnd();

				if (startDay.isAfter(today)) {
					runnedCourse.setCurrentState(RUN_COURSE_STATE_RECRUITING);

				} else if (endDate.isBefore(today)) {
					runnedCourse.setCurrentState(RUN_COURSE_STATE_ENDED);

				} else {
					runnedCourse.setCurrentState(RUN_COURSE_STATE_RUNNING);

				}

			}
		}

	}

	@Override
	public void defineCourseLaunchStatus(RunnedCourse runnedCourse) {

		int runnedCourseStatusId;
		runnedCourseStatusId = runnedCourse.getCourseStatus();

		if (runnedCourseStatusId == RUN_COURSE_STATUS_CANSELLED) {
			runnedCourse.setCurrentState(RUN_COURSE_STATE_CANSELLED);

		} else if (runnedCourseStatusId == RUN_COURSE_STATUS_RECRUTING) {

			LocalDate today = LocalDate.now();
			LocalDate startDay = runnedCourse.getDateOfStart();
			LocalDate endDate = runnedCourse.getDateOfEnd();

			if (startDay.isAfter(today)) {
				runnedCourse.setCurrentState(RUN_COURSE_STATE_RECRUITING);

			} else if (endDate.isBefore(today)) {
				runnedCourse.setCurrentState(RUN_COURSE_STATE_ENDED);

			} else {
				runnedCourse.setCurrentState(RUN_COURSE_STATE_RUNNING);
			}
		}
	}
	
	@Override
	public boolean isStudentOnRunCourse(int userId, RunnedCourse info) {

		List<StudentOnCourse> studentsOnCourse;
		studentsOnCourse = info.getStudentsOnCourse();

		for (StudentOnCourse student : studentsOnCourse) {

			int studentId;
			studentId = student.getUserId();

			if (studentId == userId) {
				return true;
			}
		}
		return false;
	}

	@Override
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

}
