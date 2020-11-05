package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.StudentStatusService;
import by.epamtc.facultative.service.impl.CourseInfoServiceImpl;

public class ApproveStudentForCourseCommand implements Command {

	private final String APPLICATION_SUCCESS_PAGE = "WEB-INF/jsp/success-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_student_approved";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");

		if (userLogin != null) {

			int runCourseId = Integer.parseInt(request.getParameter("runCourseId"));
			int studentId = Integer.parseInt(request.getParameter("studentId"));

			StudentStatusService studentStatusService = StudentStatusService.getInstance();
			studentStatusService.approveStudentOnCourse(studentId, runCourseId);

			try {

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
//				
//				request.setAttribute("message", "student_approved");
//				request.setAttribute("run_course_id", runCourseId);
//				request.getRequestDispatcher(APPLICATION_SUCCESS_PAGE).forward(request, response);

			} catch (IOException e) {
				// Я обработаю, честное слово
			}
		} else {
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
