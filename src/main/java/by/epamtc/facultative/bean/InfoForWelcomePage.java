package by.epamtc.facultative.bean;

import java.io.Serializable;

public class InfoForWelcomePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String leastPopularCourse;
	private String mostPopularCourse;

	private int amountOfStudents;

	public InfoForWelcomePage() {
		super();
	}

	public String getLeastPopularCourse() {
		return leastPopularCourse;
	}

	public String getMostPopularCourse() {
		return mostPopularCourse;
	}

	public int getAmountOfStudents() {
		return amountOfStudents;
	}

	public void setLeastPopularCourse(String leastPopularCourse) {
		this.leastPopularCourse = leastPopularCourse;
	}

	public void setMostPopularCourse(String mostPopularCourse) {
		this.mostPopularCourse = mostPopularCourse;
	}

	public void setAmountOfStudents(int amountOfStudents) {
		this.amountOfStudents = amountOfStudents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amountOfStudents;
		result = prime * result + ((leastPopularCourse == null) ? 0 : leastPopularCourse.hashCode());
		result = prime * result + ((mostPopularCourse == null) ? 0 : mostPopularCourse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoForWelcomePage other = (InfoForWelcomePage) obj;
		if (amountOfStudents != other.amountOfStudents)
			return false;
		if (leastPopularCourse == null) {
			if (other.leastPopularCourse != null)
				return false;
		} else if (!leastPopularCourse.equals(other.leastPopularCourse))
			return false;
		if (mostPopularCourse == null) {
			if (other.mostPopularCourse != null)
				return false;
		} else if (!mostPopularCourse.equals(other.mostPopularCourse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoForWelcomePage [leastPopularCourse=" + leastPopularCourse + ", mostPopularCourse="
				+ mostPopularCourse + ", amountOfStudents=" + amountOfStudents + "]";
	}

}
