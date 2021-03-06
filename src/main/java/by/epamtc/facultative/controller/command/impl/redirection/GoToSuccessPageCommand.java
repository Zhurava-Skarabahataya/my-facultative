package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class GoToSuccessPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToSuccessPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	private final String APPLICATION_SUCCESS_PAGE = "WEB-INF/jsp/success-page.jsp";

	private final String REQUEST_ATTRIBUTE_MESSAGE = "message";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String message = request.getParameter(REQUEST_ATTRIBUTE_MESSAGE);

		try {
			request.setAttribute(REQUEST_ATTRIBUTE_MESSAGE, message);

			request.getRequestDispatcher(APPLICATION_SUCCESS_PAGE).forward(request, response);

		} catch (ServletException | IOException e) {
			logger.error(e);

			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}

	}

}
