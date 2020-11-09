package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class ViewAnotherUserRunCoursesCommand implements Command {

	private static final Logger logger = Logger.getLogger(ViewAnotherUserRunCoursesCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	
	private final String AVAILABLE_RUN_COURSES_PATH = "WEB-INF/jsp/available-run-courses-page.jsp";


	private final String REQUEST_PARAMETER_USER_ID = "userId";
	private final String REQUEST_PARAMETER_USER_ROLE_ID = "userRole";
	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			int userIdFromRequest;
			int userRoleIdFromRequest;

			userIdFromRequest = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_USER_ID));
			userRoleIdFromRequest = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_USER_ROLE_ID));

			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			CourseInfoService courseInfoService = serviceProvider.getCourseInfoService();

			UserInfo userFromRequest = new UserInfo();
			userFromRequest.setUserId(userIdFromRequest);

			try {
				if (userRoleIdFromRequest == 1) {
					courseInfoService.findStudentRunCourses(userFromRequest);
				} else {
					courseInfoService.findLecturerRunCourses(userFromRequest);
				}
			} catch (ServiceException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
			
			List<RunnedCourse> userCourses = userFromRequest.getCurrentCourses();
			userCourses.addAll(userFromRequest.getEndedCourses());
			
			request.setAttribute("courses", userCourses);
			
			request.getRequestDispatcher(AVAILABLE_RUN_COURSES_PATH).forward(request, response);

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
