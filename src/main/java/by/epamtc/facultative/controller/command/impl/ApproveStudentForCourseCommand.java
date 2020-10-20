package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.StudentStatusService;

public class ApproveStudentForCourseCommand implements Command {
	
	private final String APPLICATION_SUCCESS_PAGE = "WEB-INF/jsp/application-success-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		
		if (userLogin != null) {
			
			int runCourseId = Integer.parseInt(request.getParameter("runCourseId"));
			int studentId = Integer.parseInt(request.getParameter("studentId"));
					
			StudentStatusService studentStatusService = StudentStatusService.getInstance();
			studentStatusService.approveStudentOnCourse(studentId, runCourseId);
			System.out.println(studentId);
			System.out.println("course " + runCourseId);
										
			try {
				
				request.setAttribute("message", "student_approved");
				request.setAttribute("run_course_id", runCourseId);
				request.getRequestDispatcher(APPLICATION_SUCCESS_PAGE).forward(request, response);
				
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
