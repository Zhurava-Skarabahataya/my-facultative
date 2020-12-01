package by.epamtc.facultative.service.impl;

import java.util.List;

import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.service.RatingService;

public class RatingServiceImpl implements RatingService{

	private static final RatingServiceImpl instance = new RatingServiceImpl();

	private RatingServiceImpl() {

	}

	public static RatingServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void countRatingForStudents(List<UserInfo> students) {

		for (UserInfo student : students) {

			List<Mark> marks;
			String rating;

			marks = student.getStudentMarks();

			rating = null;

			if (marks.size() != 0) {
				double sumOfMarks = 0;

				for (Mark mark : marks) {
					sumOfMarks += mark.getMarkGrade();
				}

				double averageMark = sumOfMarks / (double)marks.size();

				rating = String.format("%.1f", averageMark);
			}
			student.setStudentRating(rating);
		}
	}

	@Override
	public void countRatingForStudent(UserInfo student) {

		List<Mark> marks;
		marks = student.getStudentMarks();

		String rating = null;

		if (marks.size() != 0) {
			double sumOfMarks = 0;

			for (Mark mark : marks) {
				sumOfMarks += mark.getMarkGrade();
			}
			double averageMark = sumOfMarks / (double) marks.size();

			rating = String.format("%.1f", averageMark);
		}

		student.setStudentRating(rating);
	}

}
