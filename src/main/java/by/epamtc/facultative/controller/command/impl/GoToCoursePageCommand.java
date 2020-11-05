package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.impl.CourseInfoServiceImpl;

public class GoToCoursePageCommand implements Command {

	private final String COURSE_PAGE_PATH = "WEB-INF/jsp/course-page.jsp";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		
		Course course = new Course();
		course.setCourseId(courseId);
		
		CourseInfoServiceImpl courseInfoService = CourseInfoServiceImpl.getInstance();
		courseInfoService.findInfoAboutCourse(course);
		
		request.setAttribute("course", course);
		
		try {
			request.getRequestDispatcher(COURSE_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}
		

	}

}
