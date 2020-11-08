package by.epamtc.facultative.controller.command.impl.localization;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class ChangeLanguageCommand implements Command{
	
	private static final Logger logger = Logger.getLogger(ChangeLanguageCommand.class);

	public  final String PARAMETER_COMMAND = "commandToLanguageChanger";
	public  final String PARAMETER_LOCAL = "local";
	public  final String COMMAND_PREFIX = "?command=";
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String command = (String) session.getAttribute(PARAMETER_COMMAND);

		request.getSession(true).setAttribute(PARAMETER_LOCAL, request.getParameter(PARAMETER_LOCAL));

		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_PREFIX + command);
			
		} catch (IOException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}
	}

	
}
