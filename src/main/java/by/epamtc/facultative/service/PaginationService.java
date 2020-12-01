package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.RunnedCourse;

public interface PaginationService {

	List<RunnedCourse> findCourses(List<RunnedCourse> allCourses, int page, int cOURSES_ON_PAGE);

}
