package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.MidiDevice.Info;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class GoToRunCoursePageCommand implements Command {

	private final String RUN_COURSE_PAGE_PATH = "WEB-INF/jsp/run-course-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");

		CourseInfoService courseInfoProvider = CourseInfoService.getInstance();
		int runCourseId = Integer.parseInt(request.getParameter("run_course_id"));
		RunnedCourse runCourse = courseInfoProvider.findRunCourseById(runCourseId);
		
		System.out.println(runCourse.getStudentAmount());
		System.out.println(runCourse.getStudentLimit());

		request.setAttribute("run_course", runCourse);
		
		if (userLogin != null) {
			UserInfo userInfo = (UserInfo) session.getAttribute("bean");

			int userId = userInfo.getUserId();
			int userRoleId = userInfo.getUserRoleId();
			

			if (userRoleId == 1) {

				int userStatusOnCourse = courseInfoProvider.getUserOnCourseApprovalStatusId(userId, runCourse);
				
				request.setAttribute("user_approval_status_id", userStatusOnCourse);
			} else {
				// Если препод, чтобы список студней видел и оценки им ставил
			}
		}
		try {
			request.getRequestDispatcher(RUN_COURSE_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}

	}

}
