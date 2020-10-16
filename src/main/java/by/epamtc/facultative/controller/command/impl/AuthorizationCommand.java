package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.AuthorizationUserService;

public class AuthorizationCommand implements Command {

	private static final String PARAMETER_LOGIN = "login";
	private static final String PARAMETER_PASSWORD = "password";

	private static final String SESSION_ATTRIBUTE_USER_LOGIN = "userLogin";

	private static final String SUCCESS_REDIRECT_PARAMETERS = "?command=go_to_welcome_page";
	private static final String AUTHORIZATION_PAGE_PATH = "WEB-INF/jsp/authorization-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(PARAMETER_LOGIN);
		String password = request.getParameter(PARAMETER_PASSWORD);

		UserAuthorizationInfo userAuthorizationInfo = new UserAuthorizationInfo();
		userAuthorizationInfo.setLogin(login);
		userAuthorizationInfo.setPassword(password);

		AuthorizationUserService authorizationUserService = AuthorizationUserService.getInstance();
		
		if (authorizationUserService.execute(userAuthorizationInfo)) {

			HttpSession session = request.getSession();
			session.setAttribute(SESSION_ATTRIBUTE_USER_LOGIN, login);

			try {
				response.sendRedirect(request.getRequestURI() + SUCCESS_REDIRECT_PARAMETERS);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			
			request.setAttribute("messageFromServlet", "К сожалению, такого пользователя не найдено.");
			
			try {
				request.getRequestDispatcher(AUTHORIZATION_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				//ОБРАБООООТАЙ
				e.printStackTrace();
			} catch (IOException e) {
				//ОБРАБООООТАЙ
				e.printStackTrace();
			}
		}
	}

}
