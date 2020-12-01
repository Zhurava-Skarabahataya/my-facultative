package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class RunnedCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	Course infoAboutCourse;

	String courseName;
	String shedule;
	String lecturerName;
	String lecturerLogin;
	String lecturerPhotoLink;
	String courseStatusName;
	String studentStatusName;

	LocalDate dateOfStart;
	LocalDate dateOfEnd;

	int courseId;
	int runCourseId;
	int courseStatus;
	int currentState;
	int lecturerId;
	int classroomNumber;
	int studentLimit;
	int studentAmount;
	int studentResult;

	List<StudentOnCourse> studentsOnCourse;

	public RunnedCourse() {

	}

	public Course getInfoAboutCourse() {
		return infoAboutCourse;
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

	public String getLecturerLogin() {
		return lecturerLogin;
	}

	public String getLecturerPhotoLink() {
		return lecturerPhotoLink;
	}

	public String getCourseStatusName() {
		return courseStatusName;
	}

	public String getStudentStatusName() {
		return studentStatusName;
	}

	public LocalDate getDateOfStart() {
		return dateOfStart;
	}

	public LocalDate getDateOfEnd() {
		return dateOfEnd;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getRunCourseId() {
		return runCourseId;
	}

	public int getCourseStatus() {
		return courseStatus;
	}

	public int getCurrentState() {
		return currentState;
	}

	public int getLecturerId() {
		return lecturerId;
	}

	public int getClassroomNumber() {
		return classroomNumber;
	}

	public int getStudentLimit() {
		return studentLimit;
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public int getStudentResult() {
		return studentResult;
	}

	public List<StudentOnCourse> getStudentsOnCourse() {
		return studentsOnCourse;
	}

	public void setInfoAboutCourse(Course infoAboutCourse) {
		this.infoAboutCourse = infoAboutCourse;
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

	public void setLecturerLogin(String lecturerLogin) {
		this.lecturerLogin = lecturerLogin;
	}

	public void setLecturerPhotoLink(String lecturerPhotoLink) {
		this.lecturerPhotoLink = lecturerPhotoLink;
	}

	public void setCourseStatusName(String courseStatusName) {
		this.courseStatusName = courseStatusName;
	}

	public void setStudentStatusName(String studentStatusName) {
		this.studentStatusName = studentStatusName;
	}

	public void setDateOfStart(LocalDate dateOfStart) {
		this.dateOfStart = dateOfStart;
	}

	public void setDateOfEnd(LocalDate dateOfEnd) {
		this.dateOfEnd = dateOfEnd;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setRunCourseId(int runCourseId) {
		this.runCourseId = runCourseId;
	}

	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}

	public void setClassroomNumber(int classroomNumber) {
		this.classroomNumber = classroomNumber;
	}

	public void setStudentLimit(int studentLimit) {
		this.studentLimit = studentLimit;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}

	public void setStudentResult(int studentResult) {
		this.studentResult = studentResult;
	}

	public void setStudentsOnCourse(List<StudentOnCourse> studentsOnCourse) {
		this.studentsOnCourse = studentsOnCourse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + classroomNumber;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + courseStatus;
		result = prime * result + ((courseStatusName == null) ? 0 : courseStatusName.hashCode());
		result = prime * result + currentState;
		result = prime * result + ((dateOfEnd == null) ? 0 : dateOfEnd.hashCode());
		result = prime * result + ((dateOfStart == null) ? 0 : dateOfStart.hashCode());
		result = prime * result + ((infoAboutCourse == null) ? 0 : infoAboutCourse.hashCode());
		result = prime * result + lecturerId;
		result = prime * result + ((lecturerLogin == null) ? 0 : lecturerLogin.hashCode());
		result = prime * result + ((lecturerName == null) ? 0 : lecturerName.hashCode());
		result = prime * result + ((lecturerPhotoLink == null) ? 0 : lecturerPhotoLink.hashCode());
		result = prime * result + runCourseId;
		result = prime * result + ((shedule == null) ? 0 : shedule.hashCode());
		result = prime * result + studentAmount;
		result = prime * result + studentLimit;
		result = prime * result + studentResult;
		result = prime * result + ((studentStatusName == null) ? 0 : studentStatusName.hashCode());
		result = prime * result + ((studentsOnCourse == null) ? 0 : studentsOnCourse.hashCode());
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
		RunnedCourse other = (RunnedCourse) obj;
		if (classroomNumber != other.classroomNumber)
			return false;
		if (courseId != other.courseId)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseStatus != other.courseStatus)
			return false;
		if (courseStatusName == null) {
			if (other.courseStatusName != null)
				return false;
		} else if (!courseStatusName.equals(other.courseStatusName))
			return false;
		if (currentState != other.currentState)
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
		if (infoAboutCourse == null) {
			if (other.infoAboutCourse != null)
				return false;
		} else if (!infoAboutCourse.equals(other.infoAboutCourse))
			return false;
		if (lecturerId != other.lecturerId)
			return false;
		if (lecturerLogin == null) {
			if (other.lecturerLogin != null)
				return false;
		} else if (!lecturerLogin.equals(other.lecturerLogin))
			return false;
		if (lecturerName == null) {
			if (other.lecturerName != null)
				return false;
		} else if (!lecturerName.equals(other.lecturerName))
			return false;
		if (lecturerPhotoLink == null) {
			if (other.lecturerPhotoLink != null)
				return false;
		} else if (!lecturerPhotoLink.equals(other.lecturerPhotoLink))
			return false;
		if (runCourseId != other.runCourseId)
			return false;
		if (shedule == null) {
			if (other.shedule != null)
				return false;
		} else if (!shedule.equals(other.shedule))
			return false;
		if (studentAmount != other.studentAmount)
			return false;
		if (studentLimit != other.studentLimit)
			return false;
		if (studentResult != other.studentResult)
			return false;
		if (studentStatusName == null) {
			if (other.studentStatusName != null)
				return false;
		} else if (!studentStatusName.equals(other.studentStatusName))
			return false;
		if (studentsOnCourse == null) {
			if (other.studentsOnCourse != null)
				return false;
		} else if (!studentsOnCourse.equals(other.studentsOnCourse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RunnedCourse [infoAboutCourse=" + infoAboutCourse + ", courseName=" + courseName + ", shedule="
				+ shedule + ", lecturerName=" + lecturerName + ", lecturerLogin=" + lecturerLogin
				+ ", lecturerPhotoLink=" + lecturerPhotoLink + ", courseStatusName=" + courseStatusName
				+ ", studentStatusName=" + studentStatusName + ", dateOfStart=" + dateOfStart + ", dateOfEnd="
				+ dateOfEnd + ", courseId=" + courseId + ", runCourseId=" + runCourseId + ", courseStatus="
				+ courseStatus + ", currentState=" + currentState + ", lecturerId=" + lecturerId + ", classroomNumber="
				+ classroomNumber + ", studentLimit=" + studentLimit + ", studentAmount=" + studentAmount
				+ ", studentResult=" + studentResult + ", studentsOnCourse=" + studentsOnCourse + "]";
	}

	

}
