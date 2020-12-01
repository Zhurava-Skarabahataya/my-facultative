package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.PaginationService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToAvailableRunCoursesPage implements Command {

	private static final Logger logger = Logger.getLogger(GoToAvailableRunCoursesPage.class);

	private final String REQUEST_PARAMETER_COURSES = "courses";
	private final String REQUEST_PARAMETER_PAGE = "page";
	private final String REQUEST_PARAMETER_PAGE_AMOUNT = "pageAmount";
	private final int COURSES_ON_PAGE = 5;
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";

	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	private final String AVAILABLE_RUN_COURSES_PATH = "WEB-INF/jsp/available-run-courses-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<RunnedCourse> allCourses = null;
		List<RunnedCourse> courses = null;

		CourseInfoService courseInfoService = ServiceProvider.getInstance().getCourseInfoService();

		try {
			int page = 1;

			if (request.getParameter(REQUEST_PARAMETER_PAGE) != null) {
				page = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_PAGE));
			}

			allCourses = courseInfoService.findAllAvailableRunCourses();
			allCourses.sort(Comparator.comparing(RunnedCourse::getDateOfStart));

			int pageAmount = (int) Math.ceil((double) allCourses.size() / COURSES_ON_PAGE);

			PaginationService paginationService = ServiceProvider.getInstance().getPaginationService();

			courses = paginationService.findCourses(allCourses, page, COURSES_ON_PAGE);

			request.setAttribute(REQUEST_PARAMETER_PAGE_AMOUNT, pageAmount);
			request.setAttribute(REQUEST_PARAMETER_PAGE, page);
			request.setAttribute(REQUEST_PARAMETER_COURSES, courses);
			request.getRequestDispatcher(AVAILABLE_RUN_COURSES_PATH).forward(request, response);

		} catch (ServletException | IOException | ServiceException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

		}

	}

}
