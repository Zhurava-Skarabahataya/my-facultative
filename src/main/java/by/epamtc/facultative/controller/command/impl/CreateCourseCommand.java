package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseCreatorService;

public class CreateCourseCommand implements Command {
	
	private final String COMMAND_GO_TO_USER_COURSES_PAGE = "?command=go_to_user_courses_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(request.getParameter("courseId")); 
		int courseId = Integer.parseInt(request.getParameter("courseId"));

		LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
		LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
		
		String shedule = request.getParameter("shedule");
		int classroom = Integer.parseInt(request.getParameter("classroom"));
		int studentLimit = Integer.parseInt(request.getParameter("student_limit"));
		int courseStatus = 2;
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("bean");
		 int lecturerId = userInfo.getUserId();
		
		InfoAboutRunnedCourse infoAboutRunnedCourse = new InfoAboutRunnedCourse();
		
		infoAboutRunnedCourse.setCourseId(courseId);
		infoAboutRunnedCourse.setLecturerId(lecturerId);
		infoAboutRunnedCourse.setShedule(shedule);
		infoAboutRunnedCourse.setDateOfStart(startDate);
		infoAboutRunnedCourse.setDateOfEnd(endDate);
		
		infoAboutRunnedCourse.setClassroomNumber(classroom);
		infoAboutRunnedCourse.setStudentLimit(studentLimit);

		infoAboutRunnedCourse.setCourseStatus(courseStatus);
		
		CourseCreatorService courseCreatorService = CourseCreatorService.getInstance();
		courseCreatorService.createRunCourse(infoAboutRunnedCourse);
		

		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_USER_COURSES_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
