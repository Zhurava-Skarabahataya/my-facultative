package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseCreatorService;
import by.epamtc.facultative.service.FullNameService;

public class CreateRunCourseCommand implements Command {

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_created_course";

	private final String beanSessionAttrinuteName = "bean";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(beanSessionAttrinuteName);

		int courseId = Integer.parseInt(request.getParameter("courseId"));

		LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
		LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

		String shedule = request.getParameter("shedule");
		int classroom = Integer.parseInt(request.getParameter("classroom"));
		int studentLimit = Integer.parseInt(request.getParameter("student_limit"));
		int courseStatus = 1;

		int lecturerId = userInfo.getUserId();
		String lecturerFirstName = userInfo.getUserFirstName();
		String lecturerSecondName = userInfo.getUserSecondName();
		String lecturerPatronymic = userInfo.getUserPatronymic();

		String lecturerFullName = FullNameService.getInstance().createFullName(lecturerFirstName, lecturerSecondName,
				lecturerPatronymic);

		RunnedCourse infoAboutRunnedCourse = new RunnedCourse();

		infoAboutRunnedCourse.setCourseId(courseId);
		infoAboutRunnedCourse.setLecturerId(lecturerId);
		infoAboutRunnedCourse.setLecturerName(lecturerFullName);
		infoAboutRunnedCourse.setShedule(shedule);
		infoAboutRunnedCourse.setDateOfStart(startDate);
		infoAboutRunnedCourse.setDateOfEnd(endDate);

		infoAboutRunnedCourse.setClassroomNumber(classroom);
		infoAboutRunnedCourse.setStudentLimit(studentLimit);

		infoAboutRunnedCourse.setCourseStatus(courseStatus);

		CourseCreatorService courseCreatorService = CourseCreatorService.getInstance();
		courseCreatorService.createRunCourse(infoAboutRunnedCourse);

		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
