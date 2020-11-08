package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GiveStudentGradeCommand implements Command {

	private static final Logger logger = Logger.getLogger(CreateRunCourseCommand.class);

	private final String COMMAND_GO_TO_RUN_COURSE_PAGE = "?command=go_to_run_course_page";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_ABOUTE_RUN_COURSE_ID = "&run_course_id=";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS = "&message=not_enough_rights_for_procedure";

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";
	
	private final String REQUEST_PARAMETER_RUN_COURSE_ID = "runCourseId";
	private final String REQUEST_PARAMETER_STUDENT_ID = "studentId";
	private final String REQUEST_PARAMETER_GRADE = "grade";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {

			UserInfo user;
			int userRole;

			user = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);
			userRole = user.getUserRoleId();

			if (userRole == 2) {

				int runCourseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_RUN_COURSE_ID));
				int studentId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_STUDENT_ID));
				int grade = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_GRADE));

				CourseInfoService courseInfoService = ServiceProvider.getInstance().getCourseInfoService();

				try {
					courseInfoService.giveStudentGraveOnRunCourse(studentId, runCourseId, grade);

					response.sendRedirect(COMMAND_GO_TO_RUN_COURSE_PAGE + MESSAGE_ABOUTE_RUN_COURSE_ID + runCourseId);

				} catch (IOException | ServiceException e) {
					logger.error(e);
					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
				}
			} else {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS);
			}
			
		} else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED);
			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}

	}

}
