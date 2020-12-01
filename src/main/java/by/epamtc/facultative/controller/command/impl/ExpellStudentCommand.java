package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.StudentStatusService;
import by.epamtc.facultative.service.exception.ServiceException;

public class ExpellStudentCommand implements Command {

private static final Logger logger = Logger.getLogger(DisapproveStudentForCourseCommand.class);
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";

	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_student_expelled";

	
	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String REQUEST_PARAMETER_USER_ID = "userId";



	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {
			
			try {
				int userId;
				userId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_USER_ID));

				StudentStatusService studentStatusService = ServiceProvider.getInstance().getStudentStatusService();
				studentStatusService.expelStudent(userId);

			} catch (ServiceException e) {
				
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
			
			response.sendRedirect(
					request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
	
		}
		else {
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
