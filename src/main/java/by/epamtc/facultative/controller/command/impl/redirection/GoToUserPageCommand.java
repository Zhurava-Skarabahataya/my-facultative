package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class GoToUserPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToUserPageCommand.class);
	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String USER_PAGE_PATH = "WEB-INF/jsp/user-page.jsp";

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			try {
				request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);

			} catch (ServletException | IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
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
