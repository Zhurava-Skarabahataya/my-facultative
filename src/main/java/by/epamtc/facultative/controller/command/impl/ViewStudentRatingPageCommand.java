package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class ViewStudentRatingPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(ViewStudentRatingPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String REQUEST_ATTRIBUTE_LOGIN = "userLogin";
	private final String REQUEST_ATTRIBUTE_ID = "userId";
	private final String REQUEST_ATTRIBUTE_STUDENT = "student";

	private final String SESSION_ATTRIBUTE_BEAN = "bean";
	private final String USER_RATING_PAGE_PATH = "WEB-INF/jsp/user-rating-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			String userLoginFromRequest;
			UserInfo studentUserInfo;

			userLoginFromRequest = request.getParameter(REQUEST_ATTRIBUTE_LOGIN);
			studentUserInfo = new UserInfo();

			if (userLoginFromRequest == null) {
				studentUserInfo.setUserLogin(userLogin);

			} else {
				studentUserInfo.setUserLogin(userLoginFromRequest);
			}
			
			UserInfoService userService = ServiceProvider.getInstance().getUserInfoService();
			try {

				userService.findUserRating(studentUserInfo);

				request.setAttribute(REQUEST_ATTRIBUTE_STUDENT, studentUserInfo);

				request.getRequestDispatcher(USER_RATING_PAGE_PATH).forward(request, response);

			} catch (ServletException | IOException | ServiceException e) {
				e.printStackTrace();
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		} else{

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
