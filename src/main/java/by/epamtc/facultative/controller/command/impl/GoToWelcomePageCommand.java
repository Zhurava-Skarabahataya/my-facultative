package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.InfoForWelcomePage;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.WelcomePageService;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToWelcomePageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToWelcomePageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	
	private final String WELCOME_PAGE_PATH = "WEB-INF/jsp/welcome-page.jsp";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String REQUEST_ATTRIBUTE_LOGIN = "info";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute(SESSION_ATTRIBUTE_LOGIN) == null) {
			session.setAttribute(SESSION_ATTRIBUTE_LOGIN, null);
		}

		try {
			InfoForWelcomePage infoForWelcomePage = new InfoForWelcomePage();
			
			WelcomePageService welcomePageService = ServiceProvider.getInstance().getWelcomePageService();
			welcomePageService.getInfo(infoForWelcomePage);
			
			request.setAttribute(REQUEST_ATTRIBUTE_LOGIN, infoForWelcomePage);
			request.getRequestDispatcher(WELCOME_PAGE_PATH).forward(request, response);

		} catch (ServletException | IOException | ServiceException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

		}

	}

}
