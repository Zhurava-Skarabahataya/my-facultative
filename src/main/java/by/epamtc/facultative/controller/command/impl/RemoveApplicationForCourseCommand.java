package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class RemoveApplicationForCourseCommand implements Command {

	private static final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private static final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_remove_apply";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		
		if (userLogin != null) {
			
			int runCourseId = Integer.parseInt(request.getParameter("run_course_id"));
			UserInfo user = (UserInfo) session.getAttribute("bean");
			int userId = user.getUserId();
							
			try {
				CourseInfoService courseInfoProvider = CourseInfoService.getInstance();
				courseInfoProvider.removeApplicationStudentForRunCourse(userId, runCourseId);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
			}  catch (IOException e) {
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
