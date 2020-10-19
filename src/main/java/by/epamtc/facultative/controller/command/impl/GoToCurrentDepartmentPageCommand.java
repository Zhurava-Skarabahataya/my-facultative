package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.DepartmentInfoProviderService;

public class GoToCurrentDepartmentPageCommand implements Command {
	
	private final String DEPARTMENTS_PAGE_PATH = "WEB-INF/jsp/department-page.jsp";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		int departmentId = Integer.parseInt(request.getParameter("department_id"));
		String deanName = request.getParameter("dean_name");
		String deanPhotoLink = request.getParameter("dean_photo_link");
		String departmentName = request.getParameter("department_name");
		
		DepartmentInfoProviderService departmentService = DepartmentInfoProviderService.getInstance();
		Department department = new Department();
		department.setDepartmentID(departmentId);
		department.setDeanName(deanName);
		department.setDeanImagePath(deanPhotoLink);
		department.setDepartmentName(departmentName);
		
		departmentService.findLecturersAndCoursesForDepartment(department);
		
		request.setAttribute("department", department);
		System.out.println(department);
		
		
		try {
			request.getRequestDispatcher(DEPARTMENTS_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}

	}

}
