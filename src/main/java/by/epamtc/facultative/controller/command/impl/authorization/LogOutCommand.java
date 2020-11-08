package by.epamtc.facultative.controller.command.impl.authorization;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class LogOutCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(LogOutCommand.class);

	private final String SESSION_PARAMETER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String COMMAND_GO_TO_WELCOME_PAGE = "?command=go_to_welcome_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_PARAMETER_LOGIN, null);
		session.setAttribute(SESSION_PARAMETER_BEAN, null);
		
		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_WELCOME_PAGE);
			
		} catch (IOException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}
		
	}

}
