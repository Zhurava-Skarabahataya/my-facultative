package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;

public class GoToCreateCoursePage implements Command {

	private static final String CREATE_COURSE_PAGE_PATH = "WEB-INF/jsp/create-new-run-course-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String MESSAGE_TO_ERROR_PAGE = "errorMessage";
	private final String ERROR_CAUSE_NOT_APPROVED = "user_not_approved";
	private final String ERROR_CAUSE_NOT_AUTHORIZED = "user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("userLogin");

		if (login != null) {
			UserInfo userPageInfo = (UserInfo) session.getAttribute("bean");
			int lecturerStatusId = userPageInfo.getUserStatusId();

			if (lecturerStatusId != 2) {
				request.setAttribute(MESSAGE_TO_ERROR_PAGE, ERROR_CAUSE_NOT_APPROVED);
				try {
					request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
				int userDeparttmentId = userPageInfo.getUserFacultyId();
				List<Course> availableCoursesForDepartment = null;

				CourseInfoService courseInfoProvider = CourseInfoService.getInstance();
				availableCoursesForDepartment = courseInfoProvider.findAvailableCoursesForDepartment(userDeparttmentId);

				request.setAttribute("listOfCourses", availableCoursesForDepartment);

				try {
					request.getRequestDispatcher(CREATE_COURSE_PAGE_PATH).forward(request, response);

				} catch (ServletException e) {
					e.printStackTrace();
					// ОБРАБООООООТАЙ//Я обработаю, честное слово
				} catch (IOException e) {
					e.printStackTrace();
					// ОБРАБООООООТАЙ//Я обработаю, честное слово

				}
			}

		}

	}

}
