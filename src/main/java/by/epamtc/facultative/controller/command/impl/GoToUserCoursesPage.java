package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class GoToUserCoursesPage implements Command {

	private final String USER_COURSES_PAGE_PATH = "WEB-INF/jsp/user-courses-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		UserInfo userPageInfo = (UserInfo) session.getAttribute("bean");

		int userId = userPageInfo.getUserId();
		int userRoleId = userPageInfo.getUserRoleId();

		CourseInfoService courseInfoProvider = CourseInfoService.getInstance();
		List<RunnedCourse> courses = null;
		
		if (userRoleId == 1) {
			courses = courseInfoProvider.findStudentRunCourses(userId);
		}
		if (userRoleId == 2) {
			courses = courseInfoProvider.findLecturerRunCourses(userId);
		}
		if (userRoleId == 3) {
			courses = courseInfoProvider.findDeanRunCourses(userId);

		}
		userPageInfo.setCourses(courses);

		if (userLogin != null) {

			try {
				request.getRequestDispatcher(USER_COURSES_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// Я обработаю, честное слово
			} catch (IOException e) {
				// Я обработаю, честное слово
			}
		} else {
			try {
				session.setAttribute("errorMessage", "Необходимо войти в систему.");
				request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
