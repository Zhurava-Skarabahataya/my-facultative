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
	
	private final String REQUEST_PARAMETER_ERROR = "errorMessage";
	private final String ERROR_MESSAGE_SERVER_PROBLEMS = "server_problems";
	
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_SERVER_ERROR = "&message=internal_server_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		UserInfo userPageInfo = (UserInfo) session.getAttribute("bean");
		
		if (userLogin != null) {
			
			int userId = userPageInfo.getUserId();
			int userRoleId = userPageInfo.getUserRoleId();

			CourseInfoService courseInfoProvider = CourseInfoService.getInstance();
			List<RunnedCourse> courses = null;
			
			if (userRoleId == 1) {
				courseInfoProvider.findStudentRunCourses(userPageInfo);
			}
			if (userRoleId == 2) {
				courseInfoProvider.findLecturerRunCourses(userPageInfo);
				
			}
			if (userRoleId == 3) {
				courseInfoProvider.findDeanRunCourses(userPageInfo);

			}
			
			
			
		}
		
		else {
			
			try {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {
				
				
				// LOG
				
					request.setAttribute(REQUEST_PARAMETER_ERROR, ERROR_MESSAGE_SERVER_PROBLEMS);
					request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
				

			}
			
		}

		
		

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
