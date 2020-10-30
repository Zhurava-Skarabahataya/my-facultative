package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class GiveStudentGradeCommand implements Command {
	
	private final String APPLICATION_SUCCESS_PAGE = "WEB-INF/jsp/success-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_RUN_COURSE_PAGE = "?command=go_to_run_course_page";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_student_graded";
	private final String MESSAGE_ABOUTE_RUN_COURSE_ID = "&run_course_id=";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		
		if (userLogin != null) {
			
			UserInfo user = (UserInfo) session.getAttribute("bean");
			int userRole = user.getUserRoleId();
			
			if (userRole == 2) {
				
				int runCourseId = Integer.parseInt(request.getParameter("runCourseId"));
				int studentId = Integer.parseInt(request.getParameter("studentId"));
				int grade = Integer.parseInt(request.getParameter("grade"));
				
				CourseInfoService courseInfoService = CourseInfoService.getInstance();
				courseInfoService.giveStudentGraveOnRunCourse(studentId, runCourseId, grade);
				
				try {
					response.sendRedirect(COMMAND_GO_TO_RUN_COURSE_PAGE + MESSAGE_ABOUTE_RUN_COURSE_ID + runCourseId);
//					response.sendRedirect(
//							request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//УСПЭХ и назад на страницу курса
			}
			
			else{
				//Если у нас не препод зашел
			}
			
			
		}
		
		else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}
