package by.epamtc.facultative.controller.command.impl.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class GoToRegistrationPageCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(GoToRegistrationPageCommand.class);

	private final String REGISTRATION_PAGE_PATH = "WEB-INF/jsp/registration-page.jsp";

	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";

	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	private final String SESSION_PARAMETER_LOGIN = "userLogin";
	private final String REQUEST_PARAMETER_LOGIN = "message";

	private final String ATTRIBUTE_TO_REQUEST = "message_from_registration";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_LOGIN);

		if (userLogin != null) {

			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);
				
			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		} else {

			String messageFromRegistrationCommand = request.getParameter(REQUEST_PARAMETER_LOGIN);

			if (messageFromRegistrationCommand != null) {
				request.setAttribute(ATTRIBUTE_TO_REQUEST, messageFromRegistrationCommand);
			}

			try {
				request.getRequestDispatcher(REGISTRATION_PAGE_PATH).forward(request, response);

			} catch (ServletException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

			}
		}
	}

}
