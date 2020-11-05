package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.impl.DepartmentInfoServiceImpl;

public class GoToDepartmentsPageCommand implements Command {

private final String DEPARTMENTS_PAGE_PATH = "WEB-INF/jsp/departments-page.jsp";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		List <Department> departments;
		departments = null;
		
		DepartmentInfoServiceImpl departmentService = DepartmentInfoServiceImpl.getInstance();
		departments = departmentService.findAllDepartmentsInfo();
		
		request.setAttribute("departments", departments);
		
		
		try {
			request.getRequestDispatcher(DEPARTMENTS_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}
		

	}
}
