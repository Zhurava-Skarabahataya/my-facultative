package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class GoToContactsPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToContactsPageCommand.class);

	private final String CONTACTS_PAGE_PATH = "WEB-INF/jsp/contacts-page.jsp";

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			request.getRequestDispatcher(CONTACTS_PAGE_PATH).forward(request, response);

		} catch (ServletException | IOException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

		}

	}

}
