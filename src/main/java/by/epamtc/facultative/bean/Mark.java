package by.epamtc.facultative.bean;

import java.io.Serializable;

public class Mark implements Serializable {

	private static final long serialVersionUID = 1L;

	private int markGrade;
	private int runCourseId;
	private String courseTitle;

	public Mark() {

	}

	public int getMarkGrade() {
		return markGrade;
	}

	public int getRunCourseId() {
		return runCourseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setMarkGrade(int markGrade) {
		this.markGrade = markGrade;
	}

	public void setRunCourseId(int runCourseId) {
		this.runCourseId = runCourseId;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseTitle == null) ? 0 : courseTitle.hashCode());
		result = prime * result + markGrade;
		result = prime * result + runCourseId;
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
		Mark other = (Mark) obj;
		if (courseTitle == null) {
			if (other.courseTitle != null)
				return false;
		} else if (!courseTitle.equals(other.courseTitle))
			return false;
		if (markGrade != other.markGrade)
			return false;
		if (runCourseId != other.runCourseId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mark [markGrade=" + markGrade + ", runCourseId=" + runCourseId + ", courseTitle=" + courseTitle + "]";
	}

}
