package by.epamtc.facultative.controller.command.impl.authorization;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.AuthorizationService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.impl.UserInfoServiceImpl;

public class AuthorizationCommand implements Command {

	private static final Logger logger = Logger.getLogger(AuthorizationCommand.class);

	private final String PARAMETER_LOGIN = "login";
	private final String PARAMETER_PASSWORD = "password";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String REQUEST_ATTRIBUTE_BEAN = "bean";

	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";

	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_authorization";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);

			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		} else {

			String login;
			String password;

			login = request.getParameter(PARAMETER_LOGIN);
			password = request.getParameter(PARAMETER_PASSWORD);

			UserAuthorizationInfo userAuthorizationInfo = new UserAuthorizationInfo();
			userAuthorizationInfo.setLogin(login);
			userAuthorizationInfo.setPassword(password);

			AuthorizationService authorizationUserService = ServiceProvider.getInstance().getAuthorizationUserService();

			boolean isAuthorizationSuccessful;

			try {
				isAuthorizationSuccessful = authorizationUserService.execute(userAuthorizationInfo);

				if (isAuthorizationSuccessful) {

					UserInfo userInfo = new UserInfo();
					userInfo.setUserLogin(login);

					UserInfoServiceImpl userPageInfoProviderService = UserInfoServiceImpl.getInstance();
					userPageInfoProviderService.findUserInfo(userInfo);

					session.setAttribute(REQUEST_ATTRIBUTE_BEAN, userInfo);
					session.setAttribute(SESSION_ATTRIBUTE_LOGIN, login);

					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
				}

			} catch (ServiceException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		}
	}
}