package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToUserCoursesPage implements Command {

	private static final Logger logger = Logger.getLogger(GoToUserCoursesPage.class);

	private final String USER_COURSES_PAGE_PATH = "WEB-INF/jsp/user-courses-page.jsp";

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final int USER_ROLE_STUDENT = 1;
	private final int USER_ROLE_LECTURER = 2;
	private final int USER_ROLE_RECTOR = 3;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {

			UserInfo userPageInfo;
			int userRoleId;

			userPageInfo = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);

			userRoleId = userPageInfo.getUserRoleId();

			CourseInfoService courseInfoProvider = ServiceProvider.getInstance().getCourseInfoService();

			if (userRoleId == USER_ROLE_STUDENT) {
				try {
					courseInfoProvider.findStudentRunCourses(userPageInfo);
				} catch (ServiceException e) {
					logger.error(e);

					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
				}
			}
			if (userRoleId == USER_ROLE_LECTURER) {
				try {
					courseInfoProvider.findLecturerRunCourses(userPageInfo);
				} catch (ServiceException e) {
					logger.error(e);

					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
				}
			}
			if (userRoleId == USER_ROLE_RECTOR) {
				courseInfoProvider.findDeanRunCourses(userPageInfo);

			}

			request.getRequestDispatcher(USER_COURSES_PAGE_PATH).forward(request, response);
		}

		else {
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
