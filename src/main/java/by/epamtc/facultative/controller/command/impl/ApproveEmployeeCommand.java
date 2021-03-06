package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.EmployeeStatusService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class ApproveEmployeeCommand implements Command {

	private static final Logger logger = Logger.getLogger(ApproveEmployeeCommand.class);

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String REQUEST_PARAMETER_USER_LOGIN = "employeeId";
	
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_employee_approved";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {

			String employeeIdFromRequest;
			int employeeId;

			employeeIdFromRequest = request.getParameter(REQUEST_PARAMETER_USER_LOGIN);
			employeeId = Integer.parseInt(employeeIdFromRequest);

			EmployeeStatusService employeeStatusService = ServiceProvider.getInstance().getEmployeeStatusService();
			
			try {
				employeeStatusService.approveEmployee(employeeId);

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
				
			} catch (ServiceException | IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}

		else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {

				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}

	}

}
