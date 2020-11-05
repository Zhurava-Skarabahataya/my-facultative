package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.UserInfo;

public interface RatingService {

	void countRatingForStudents(List<UserInfo> students);

	public void countRatingForStudent(UserInfo student);

}
