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

public class RemoveApplicationForCourseCommand implements Command {

	private static final Logger logger = Logger.getLogger(RemoveApplicationForCourseCommand.class);

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String SESSION_ATTRIBUTE_BEAN = "bean";
	private final String REQUEST_ATTRIBUTE_RUN_COURSE_ID = "run_course_id";
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_remove_apply";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			UserInfo user;
			int runCourseId;
			int userId;

			user = (UserInfo) session.getAttribute(SESSION_ATTRIBUTE_BEAN);
			runCourseId = Integer.parseInt(request.getParameter(REQUEST_ATTRIBUTE_RUN_COURSE_ID));
			userId = user.getUserId();

			CourseInfoService courseInfoProvider = ServiceProvider.getInstance().getCourseInfoService();

			try {
				courseInfoProvider.removeApplicationStudentForRunCourse(userId, runCourseId);

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
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}

	}

}
