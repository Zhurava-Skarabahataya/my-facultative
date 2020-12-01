package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToCoursePageCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(GoToCoursePageCommand.class);

	private final String REQUEST_PARAMETER_COURSEID = "courseId";
	private final String REQUEST_PARAMETER_COURSE = "course";
	private final String COURSE_PAGE_PATH = "WEB-INF/jsp/course-page.jsp";
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int courseId;
		courseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_COURSEID));
		
		Course course = new Course();
		course.setCourseId(courseId);
		
		CourseInfoService courseInfoService = ServiceProvider.getInstance().getCourseInfoService();
		
		
		try {
			courseInfoService.findInfoAboutCourse(course);

			request.setAttribute(REQUEST_PARAMETER_COURSE, course);
			request.getRequestDispatcher(COURSE_PAGE_PATH).forward(request, response);
			
		} catch (ServletException  | IOException | ServiceException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}
		

	}

}
