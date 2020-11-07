package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseCreatorService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.impl.FullNameServiceImpl;

public class CreateRunCourseCommand implements Command {

	private static final Logger logger = Logger.getLogger(CreateRunCourseCommand.class);

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";

	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_created_course";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";

	private final String REQUEST_PARAMETER_COURSEID = "courseId";
	private final String REQUEST_PARAMETER_STARTDATE = "startDate";
	private final String REQUEST_PARAMETER_ENDDATE = "endDate";
	private final String REQUEST_PARAMETER_SHEDULE = "shedule";
	private final String REQUEST_PARAMETER_CLASSROOM = "classroom";
	private final String REQUEST_PARAMETER_STUDENT_LIMIT = "student_limit";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {
			UserInfo userInfo;

			int courseId;
			int classroom;
			int studentLimit;
			int courseStatus;
			int lecturerId;

			String shedule;
			String lecturerFirstName;
			String lecturerSecondName;
			String lecturerPatronymic;

			LocalDate startDate;
			LocalDate endDate;

			userInfo = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);
			courseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_COURSEID));

			startDate = LocalDate.parse(request.getParameter(REQUEST_PARAMETER_STARTDATE));
			endDate = LocalDate.parse(request.getParameter(REQUEST_PARAMETER_ENDDATE));

			shedule = request.getParameter(REQUEST_PARAMETER_SHEDULE);
			classroom = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_CLASSROOM));
			studentLimit = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_STUDENT_LIMIT));
			courseStatus = 1;

			lecturerId = userInfo.getUserId();
			lecturerFirstName = userInfo.getUserFirstName();
			lecturerSecondName = userInfo.getUserSecondName();
			lecturerPatronymic = userInfo.getUserPatronymic();

			String lecturerFullName = FullNameServiceImpl.getInstance().createFullName(lecturerFirstName,
					lecturerSecondName, lecturerPatronymic);

			RunnedCourse infoAboutRunnedCourse = new RunnedCourse();

			infoAboutRunnedCourse.setCourseId(courseId);
			infoAboutRunnedCourse.setLecturerId(lecturerId);
			infoAboutRunnedCourse.setLecturerName(lecturerFullName);
			infoAboutRunnedCourse.setShedule(shedule);
			infoAboutRunnedCourse.setDateOfStart(startDate);
			infoAboutRunnedCourse.setDateOfEnd(endDate);

			infoAboutRunnedCourse.setClassroomNumber(classroom);
			infoAboutRunnedCourse.setStudentLimit(studentLimit);
			infoAboutRunnedCourse.setCourseStatus(courseStatus);

			CourseCreatorService courseCreatorService = ServiceProvider.getInstance().getCourseCreatorService();

			try {
				courseCreatorService.createRunCourse(infoAboutRunnedCourse);

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);

			} catch (IOException | ServiceException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		} else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {

				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}

	}

}
