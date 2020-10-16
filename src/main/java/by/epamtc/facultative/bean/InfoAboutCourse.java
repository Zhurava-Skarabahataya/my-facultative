package by.epamtc.facultative.bean;

public class InfoAboutCourse {
	
	private String courseName;
	private String courseDescription;
	private String courseProgram;
	private String courseRequirement;

	private int courseId;
	private int courseDuration;
	private int courseDepartment;
	
	public InfoAboutCourse() {
		
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public String getCourseProgram() {
		return courseProgram;
	}

	public String getCourseRequirement() {
		return courseRequirement;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getCourseDuration() {
		return courseDuration;
	}

	public int getCourseDepartment() {
		return courseDepartment;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public void setCourseProgram(String courseProgram) {
		this.courseProgram = courseProgram;
	}

	public void setCourseRequirement(String courseRequirement) {
		this.courseRequirement = courseRequirement;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	}

	public void setCourseDepartment(int courseDepartment) {
		this.courseDepartment = courseDepartment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseDepartment;
		result = prime * result + ((courseDescription == null) ? 0 : courseDescription.hashCode());
		result = prime * result + courseDuration;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((courseProgram == null) ? 0 : courseProgram.hashCode());
		result = prime * result + ((courseRequirement == null) ? 0 : courseRequirement.hashCode());
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
		InfoAboutCourse other = (InfoAboutCourse) obj;
		if (courseDepartment != other.courseDepartment)
			return false;
		if (courseDescription == null) {
			if (other.courseDescription != null)
				return false;
		} else if (!courseDescription.equals(other.courseDescription))
			return false;
		if (courseDuration != other.courseDuration)
			return false;
		if (courseId != other.courseId)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseProgram == null) {
			if (other.courseProgram != null)
				return false;
		} else if (!courseProgram.equals(other.courseProgram))
			return false;
		if (courseRequirement == null) {
			if (other.courseRequirement != null)
				return false;
		} else if (!courseRequirement.equals(other.courseRequirement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoAboutCourse [courseName=" + courseName + ", courseDescription=" + courseDescription
				+ ", courseProgram=" + courseProgram + ", courseRequirement=" + courseRequirement + ", courseId="
				+ courseId + ", courseDuration=" + courseDuration + ", courseDepartment=" + courseDepartment + "]";
	}
	
	

}
