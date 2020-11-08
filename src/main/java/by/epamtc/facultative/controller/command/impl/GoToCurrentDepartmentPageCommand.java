package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.DepartmentInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToCurrentDepartmentPageCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(GoToCurrentDepartmentPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String DEPARTMENTS_PAGE_PATH = "WEB-INF/jsp/department-page.jsp";
	
	private final String REQUEST_PARAMETER_DEPARTMENT_ID = "department_id";
	private final String REQUEST_PARAMETER_DEAN_NAME = "dean_name";
	private final String REQUEST_PARAMETER_DEAN_PHOTO_LINK = "dean_photo_link";
	private final String REQUEST_PARAMETER_DEPARTMENT_NAME = "department_name";
	private final String REQUEST_PARAMETER_DEPARTMENT = "department";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int departmentId;
		String deanName;
		String deanPhotoLink ;
		String departmentName;

		departmentId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_DEPARTMENT_ID));
		deanName = request.getParameter(REQUEST_PARAMETER_DEAN_NAME);
		deanPhotoLink = request.getParameter(REQUEST_PARAMETER_DEAN_PHOTO_LINK);
		departmentName = request.getParameter(REQUEST_PARAMETER_DEPARTMENT_NAME);
		
		DepartmentInfoService departmentService = ServiceProvider.getInstance().getDepartmentInfoService();
		
		Department department = new Department();
		department.setDepartmentID(departmentId);
		department.setDeanName(deanName);
		department.setDeanImagePath(deanPhotoLink);
		department.setDepartmentName(departmentName);
		
		try {

			departmentService.findLecturersAndCoursesForDepartment(department);
			
			request.setAttribute(REQUEST_PARAMETER_DEPARTMENT, department);
			request.getRequestDispatcher(DEPARTMENTS_PAGE_PATH).forward(request, response);
			
		} catch (ServletException | IOException | ServiceException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}

	}

}
