package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.impl.EmployeeStatusServiceImpl;

public class ApproveEmployeeCommand implements Command {

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_employee_approved";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		// проверь, залогинен ли

		if (userLogin != null) {

			String employeeIdFromRequest = request.getParameter("employeeId");

			int employeeId = Integer.parseInt(employeeIdFromRequest);

			EmployeeStatusServiceImpl employeeStatusService = EmployeeStatusServiceImpl.getInstance();
			try {
				employeeStatusService.approveEmployee(employeeId);
				
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}

		else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
