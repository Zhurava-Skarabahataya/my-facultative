package by.epamtc.facultative.controller.command.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.controller.command.Command;

public class CreateCourseCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int courseId = Integer.parseInt(request.getParameter("course.courseID"));
		System.out.println(courseId); 

		LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
		LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
		
		String shedule = request.getParameter("shedule");
		int classroom = Integer.parseInt(request.getParameter("classroom"));
		int studentLimit = Integer.parseInt(request.getParameter("student_limit"));
		
		InfoAboutRunnedCourse infoAboutRunnedCourse = new InfoAboutRunnedCourse();
		
		infoAboutRunnedCourse.setShedule(shedule);
		infoAboutRunnedCourse.setDateOfStart(startDate);
		infoAboutRunnedCourse.setDateOfEnd(endDate);
		
		infoAboutRunnedCourse.setClassroomNumber(classroom);
		infoAboutRunnedCourse.setStudentLimit(studentLimit);

		int courseStatus = 2;
		infoAboutRunnedCourse.setCourseStatus(courseStatus);
		
	}

}
