package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.FeedbackService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class LeaveFeedbackCommand implements Command {

	private static final Logger logger = Logger.getLogger(LeaveFeedbackCommand.class);

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String SESSION_ATTRIBUTE_BEAN = "bean";

	private final String REQUEST_PARAMETER_COURSE_ID = "courseId";
	private final String REQUEST_PARAMETER_COMMENT = "comment";

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_leaving_feedback";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			UserInfo user;
			String comment;
			int courseId;
			int userId;

			user = (UserInfo) session.getAttribute(SESSION_ATTRIBUTE_BEAN);
			courseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_COURSE_ID));
			userId = user.getUserId();

			comment = request.getParameter(REQUEST_PARAMETER_COMMENT);

			FeedbackService feedbackService = ServiceProvider.getInstance().getFeedbackService();

			try {
				feedbackService.leaveFeadback(userId, courseId, comment);

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);

			} catch (IOException | ServiceException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
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
