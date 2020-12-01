package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.DepartmentInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToDepartmentsPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToDepartmentsPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	
	private final String DEPARTMENTS_PAGE_PATH = "WEB-INF/jsp/departments-page.jsp";
	
	private final String REQUEST_PARAMETER_DEPARTMENTS = "departments";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<Department> departments;
		departments = null;

		DepartmentInfoService departmentService = ServiceProvider.getInstance().getDepartmentInfoService();


		try {
			departments = departmentService.findAllDepartmentsInfo();

			request.setAttribute(REQUEST_PARAMETER_DEPARTMENTS, departments);
			request.getRequestDispatcher(DEPARTMENTS_PAGE_PATH).forward(request, response);
			
		} catch (ServletException | IOException | ServiceException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}

	}
}
