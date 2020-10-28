package by.epamtc.facultative.controller.command.impl.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.AuthorizationUserService;
import by.epamtc.facultative.service.UserInfoService;

public class AuthorizationCommand implements Command {

	private  final String PARAMETER_LOGIN = "login";
	private  final String PARAMETER_PASSWORD = "password";

	private  final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String REQUEST_ATTRIBUTE_BEAN = "bean";


	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";

	private  final String SUCCESS_REDIRECT_PARAMETERS = "?command=go_to_welcome_page";
	private  final String AUTHORIZATION_PAGE_PATH = "WEB-INF/jsp/authorization-page.jsp";
	
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_AUTHORIZATION_PAGE = "?command=go_to_authorization_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_authorization";
	private final String MESSAGE_GO_TO_AUTH_PAGE = "&message=no_user_found";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {
			// Значит, юзер уже авторизован
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);
			} catch (IOException e) {
				// ОБРТАББ
				e.printStackTrace();
			}

		} else {

			String login = request.getParameter(PARAMETER_LOGIN);
			String password = request.getParameter(PARAMETER_PASSWORD);

			UserAuthorizationInfo userAuthorizationInfo = new UserAuthorizationInfo();
			userAuthorizationInfo.setLogin(login);
			userAuthorizationInfo.setPassword(password);

			AuthorizationUserService authorizationUserService = AuthorizationUserService.getInstance();

			if (authorizationUserService.execute(userAuthorizationInfo)) {

				session.setAttribute(SESSION_ATTRIBUTE_LOGIN, login);
				
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLogin(login);

				UserInfoService userPageInfoProviderService = UserInfoService.getInstance();
				userPageInfoProviderService.findUserInfo(userInfo);

				session.setAttribute(REQUEST_ATTRIBUTE_BEAN, userInfo);
				
				System.out.println("In auth userRoleId " + userInfo.getUserRoleId());

				try {
					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {


				try {
					
					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_AUTHORIZATION_PAGE + MESSAGE_GO_TO_AUTH_PAGE);
					//request.getRequestDispatcher(AUTHORIZATION_PAGE_PATH).forward(request, response);
				} catch (IOException e) {
					// ОБРАБООООТАЙ
					e.printStackTrace();
				}
			}
		}
	}
}