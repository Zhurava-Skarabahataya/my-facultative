package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class InfoAboutRunnedCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	String courseName;
	String shedule;
	String lecturerName;
	String coordinatorName;
	String coordinatorPhone;

	LocalDate dateOfStart;
	LocalDate dateOfEnd;

	int runCourseId;
	int courseStatus;
	int classroomNumber;
	int studentLimit;

	public InfoAboutRunnedCourse() {
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getShedule() {
		return shedule;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public String getCoordinatorName() {
		return coordinatorName;
	}

	public String getCoordinatorPhone() {
		return coordinatorPhone;
	}

	public LocalDate getDateOfStart() {
		return dateOfStart;
	}

	public LocalDate getDateOfEnd() {
		return dateOfEnd;
	}

	public int getRunCourseId() {
		return runCourseId;
	}

	public int getCourseStatus() {
		return courseStatus;
	}

	public int getClassroomNumber() {
		return classroomNumber;
	}

	public int getStudentLimit() {
		return studentLimit;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setShedule(String shedule) {
		this.shedule = shedule;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public void setCoordinatorName(String coordinatorName) {
		this.coordinatorName = coordinatorName;
	}

	public void setCoordinatorPhone(String coordinatorPhone) {
		this.coordinatorPhone = coordinatorPhone;
	}

	public void setDateOfStart(LocalDate dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	public void setDateOfEnd(LocalDate dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}

	public void setRunCourseId(int runCourseId) {
		this.runCourseId = runCourseId;
	}

	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}

	public void setClassroomNumber(int classroomNumber) {
		this.classroomNumber = classroomNumber;
	}

	public void setStudentLimit(int studentLimit) {
		this.studentLimit = studentLimit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + classroomNumber;
		result = prime * result + ((coordinatorName == null) ? 0 : coordinatorName.hashCode());
		result = prime * result + ((coordinatorPhone == null) ? 0 : coordinatorPhone.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + courseStatus;
		result = prime * result + ((dateOfEnd == null) ? 0 : dateOfEnd.hashCode());
		result = prime * result + ((dateOfStart == null) ? 0 : dateOfStart.hashCode());
		result = prime * result + ((lecturerName == null) ? 0 : lecturerName.hashCode());
		result = prime * result + runCourseId;
		result = prime * result + ((shedule == null) ? 0 : shedule.hashCode());
		result = prime * result + studentLimit;
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
		InfoAboutRunnedCourse other = (InfoAboutRunnedCourse) obj;
		if (classroomNumber != other.classroomNumber)
			return false;
		if (coordinatorName == null) {
			if (other.coordinatorName != null)
				return false;
		} else if (!coordinatorName.equals(other.coordinatorName))
			return false;
		if (coordinatorPhone == null) {
			if (other.coordinatorPhone != null)
				return false;
		} else if (!coordinatorPhone.equals(other.coordinatorPhone))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseStatus != other.courseStatus)
			return false;
		if (dateOfEnd == null) {
			if (other.dateOfEnd != null)
				return false;
		} else if (!dateOfEnd.equals(other.dateOfEnd))
			return false;
		if (dateOfStart == null) {
			if (other.dateOfStart != null)
				return false;
		} else if (!dateOfStart.equals(other.dateOfStart))
			return false;
		if (lecturerName == null) {
			if (other.lecturerName != null)
				return false;
		} else if (!lecturerName.equals(other.lecturerName))
			return false;
		if (runCourseId != other.runCourseId)
			return false;
		if (shedule == null) {
			if (other.shedule != null)
				return false;
		} else if (!shedule.equals(other.shedule))
			return false;
		if (studentLimit != other.studentLimit)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoAboutRunnedCourse [courseName=" + courseName + ", shedule=" + shedule + ", lecturerName="
				+ lecturerName + ", coordinatorName=" + coordinatorName + ", coordinatorPhone=" + coordinatorPhone
				+ ", dateOfStart=" + dateOfStart + ", dateOfEnd=" + dateOfEnd + ", runCourseId=" + runCourseId
				+ ", courseStatus=" + courseStatus + ", classroomNumber=" + classroomNumber + ", studentLimit="
				+ studentLimit + "]";
	}
	
	

}
