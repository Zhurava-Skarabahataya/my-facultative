package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class GoToAvailableRunCoursesPage implements Command {

private static final String AVAILABLE_RUN_COURSES_PATH = "WEB-INF/jsp/available-run-courses-page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		List <RunnedCourse> courses = null;
		
		courses = CourseInfoService.getInstance().findAllAvailableRunCourses();
		
		
		request.setAttribute("courses", courses);
		try {
			request.getRequestDispatcher(AVAILABLE_RUN_COURSES_PATH).forward(request, response);
			
		} catch (ServletException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ			//Я обработаю, честное слово


		} catch (IOException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ			//Я обработаю, честное слово


		}
		
		
	}
	
}
