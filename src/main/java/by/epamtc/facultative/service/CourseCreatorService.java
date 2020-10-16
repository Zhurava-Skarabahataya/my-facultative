package by.epamtc.facultative.service;

public class CourseCreatorService {
	
	private static final CourseCreatorService instance = new CourseCreatorService();
	
	private CourseCreatorService() {
		
	}
	
	public static CourseCreatorService getInstance() {
		return instance;
	}

}
