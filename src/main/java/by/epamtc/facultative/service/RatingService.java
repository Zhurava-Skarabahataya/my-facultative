package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;

public class RatingService {
	
	private static final RatingService instance = new RatingService();
	
	private RatingService() {
		
	}
	
	public static RatingService getInstance() {
		return instance;
	}
	
	public void countRatingForStudents(List <UserInfo> students) {
		
		for (UserInfo student : students) {
			
			List <Mark> marks = student.getStudentMarks();
			
			String rating = null;
			
			if (marks.size() != 0) {
				int sum = 0;

				for (Mark mark:marks) {
					sum += mark.getMarkGrade();
				}
				
				double average = sum / marks.size();
				
				rating = String.format("%.1f",average);
			}
			
			student.setStudentRating(rating);
			
		}
		
	}
	
	public void countRatingForStudent(UserInfo student) {
		List <Mark> marks = student.getStudentMarks();
		
		String rating = null;
		
		if (marks.size() != 0) {
			int sum = 0;

			for (Mark mark:marks) {
				sum += mark.getMarkGrade();
			}
			
			double average = sum / marks.size();
			
			rating = String.format("%.1f",average);
		}
		
		student.setStudentRating(rating);
	}

}
