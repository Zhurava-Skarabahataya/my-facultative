package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.MidiDevice.Info;

import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoProvider;

public class GoToRunCoursePageCommand implements Command {

	private final String RUN_COURSE_PAGE_PATH = "WEB-INF/jsp/run-course-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");

		CourseInfoProvider courseInfoProvider = CourseInfoProvider.getInstance();
		int runCourseId = Integer.parseInt(request.getParameter("run_course_id"));
		InfoAboutRunnedCourse info = courseInfoProvider.findRunCourseById(runCourseId);

		request.setAttribute("run_course", info);
		
		if (userLogin != null) {
			UserInfo userInfo = (UserInfo) session.getAttribute("bean");

			int userId = userInfo.getUserId();
			int userRoleId = userInfo.getUserRoleId();

			

			if (userRoleId == 1) {

				int userStatusOnCourse = courseInfoProvider.getUserOnCourseApprovalStatusId(userId, info);

				System.out.println(userStatusOnCourse + " статус на курсе");

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
