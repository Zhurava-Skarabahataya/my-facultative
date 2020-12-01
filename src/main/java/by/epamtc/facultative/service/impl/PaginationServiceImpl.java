package by.epamtc.facultative.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.service.PaginationService;

public class PaginationServiceImpl implements PaginationService {

	private static final PaginationServiceImpl instance = new PaginationServiceImpl();

	private final int COURSES_ON_PAGE = 5;
	private final int OFFSET = 1;

	private PaginationServiceImpl() {

	}

	public static PaginationServiceImpl getInstance() {
		return instance;
	}

	@Override
	public List<RunnedCourse> findCourses(List<RunnedCourse> allCourses, int page, int coursesOnPage) {

		List<RunnedCourse> courses = new ArrayList<RunnedCourse>();

		int startIndex;
		int endIndex;

		startIndex = (page - OFFSET) * coursesOnPage;

		endIndex = startIndex + COURSES_ON_PAGE;

		if (endIndex >= allCourses.size()) {
			endIndex = allCourses.size();
		}

		for (int index = startIndex; index < endIndex; index++) {

			courses.add(allCourses.get(index));
		}

		return courses;
	}

}
