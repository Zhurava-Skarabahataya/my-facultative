package by.epamtc.facultative.controller.command.impl.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;

public class GoToAuthorizationPageCommand implements Command {

	private final String AUTHORIZATION_PAGE_PATH = "WEB-INF/jsp/authorization-page.jsp";
	
	private final String ATTRIBUTE_TO_REQUEST = "message_from_authorization";


	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";

	private final String SESSION_PARAMETER_LOGIN = "userLogin";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute(SESSION_PARAMETER_LOGIN);

		if (userLogin != null) {
			// Значит, юзер уже авторизован
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String messageFromRegistrationCommand = request.getParameter("message");

			if (messageFromRegistrationCommand != null) {

				request.setAttribute(ATTRIBUTE_TO_REQUEST, messageFromRegistrationCommand);
			}

			try {
				request.getRequestDispatcher(AUTHORIZATION_PAGE_PATH).forward(request, response);

			} catch (ServletException e) {
				e.printStackTrace();
				// ОБРАБООООООТАЙ //Я обработаю, честное слово

			} catch (IOException e) {
				e.printStackTrace();
				// ОБРАБООООООТАЙ //Я обработаю, честное слово

			}
		}

	}

}
