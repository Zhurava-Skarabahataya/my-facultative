package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class GoToUserPageEditingCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToUserPageEditingCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String EDITING_PAGE_PATH = "WEB-INF/jsp/user-page-editing.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {
			try {
				request.getRequestDispatcher(EDITING_PAGE_PATH).forward(request, response);

			} catch (ServletException | IOException e) {
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
