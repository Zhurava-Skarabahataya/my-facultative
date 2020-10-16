package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.bean.UserPageInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoProvider;

public class GoToCreateCoursePage implements Command {
	
	private static final String CREATE_COURSE_PAGE_PATH = "WEB-INF/jsp/create-new-course-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("userLogin");
		
		UserPageInfo userPageInfo = (UserPageInfo) session.getAttribute("bean");
		
		int userDeparttmentId = userPageInfo.getUserFacultyId();
		List<InfoAboutCourse> availableCoursesForDepartment = null;
		
		CourseInfoProvider courseInfoProvider = CourseInfoProvider.getInstance();
		availableCoursesForDepartment = courseInfoProvider.findAvailableCoursesForDepartment(userDeparttmentId);
		
		for (InfoAboutCourse a : availableCoursesForDepartment) {
			System.out.println(a.getCourseName());
		}
		
		request.setAttribute("listOfCourses", availableCoursesForDepartment);
		
		if (login != null) {
			try {
				request.getRequestDispatcher(CREATE_COURSE_PAGE_PATH).forward(request, response);
				
			} catch (ServletException e) {
				e.printStackTrace();
				//ОБРАБООООООТАЙ//Я обработаю, честное слово
			} catch (IOException e) {
				e.printStackTrace();
				//ОБРАБООООООТАЙ//Я обработаю, честное слово

			}
		}
		else {
			System.out.println("ERRRRORRR");
		}

	}

}
