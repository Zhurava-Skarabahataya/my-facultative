package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	String userFirstName;
	String userSecondName;
	String userPatronymic;
	String userLogin;
	String userEmail;
	String userRole;
	String userFaculty;
	String userAdress;
	String userPhone;
	String userPhotoLink;
	String studentRating;

	LocalDate userDateOfBirth;

	int userId;
	int userRoleId;
	int userStatusId;
	int userFacultyId;

	List <Mark> studentMarks;
	
	List<RunnedCourse> currentCourses;
	List<RunnedCourse> endedCourses;
	List<RunnedCourse> canselledCourses;
	

	public UserInfo() {

	}


	public String getUserFirstName() {
		return userFirstName;
	}


	public String getUserSecondName() {
		return userSecondName;
	}


	public String getUserPatronymic() {
		return userPatronymic;
	}


	public String getUserLogin() {
		return userLogin;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public String getUserRole() {
		return userRole;
	}


	public String getUserFaculty() {
		return userFaculty;
	}


	public String getUserAdress() {
		return userAdress;
	}


	public String getUserPhone() {
		return userPhone;
	}


	public String getUserPhotoLink() {
		return userPhotoLink;
	}


	public String getStudentRating() {
		return studentRating;
	}


	public LocalDate getUserDateOfBirth() {
		return userDateOfBirth;
	}


	public int getUserId() {
		return userId;
	}


	public int getUserRoleId() {
		return userRoleId;
	}


	public int getUserStatusId() {
		return userStatusId;
	}


	public int getUserFacultyId() {
		return userFacultyId;
	}


	public List<Mark> getStudentMarks() {
		return studentMarks;
	}


	public List<RunnedCourse> getCurrentCourses() {
		return currentCourses;
	}


	public List<RunnedCourse> getEndedCourses() {
		return endedCourses;
	}


	public List<RunnedCourse> getCanselledCourses() {
		return canselledCourses;
	}


	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}


	public void setUserSecondName(String userSecondName) {
		this.userSecondName = userSecondName;
	}


	public void setUserPatronymic(String userPatronymic) {
		this.userPatronymic = userPatronymic;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	public void setUserFaculty(String userFaculty) {
		this.userFaculty = userFaculty;
	}


	public void setUserAdress(String userAdress) {
		this.userAdress = userAdress;
	}


	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}


	public void setUserPhotoLink(String userPhotoLink) {
		this.userPhotoLink = userPhotoLink;
	}


	public void setStudentRating(String studentRating) {
		this.studentRating = studentRating;
	}


	public void setUserDateOfBirth(LocalDate userDateOfBirth) {
		this.userDateOfBirth = userDateOfBirth;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}


	public void setUserStatusId(int userStatusId) {
		this.userStatusId = userStatusId;
	}


	public void setUserFacultyId(int userFacultyId) {
		this.userFacultyId = userFacultyId;
	}


	public void setStudentMarks(List<Mark> studentMarks) {
		this.studentMarks = studentMarks;
	}


	public void setCurrentCourses(List<RunnedCourse> currentCourses) {
		this.currentCourses = currentCourses;
	}


	public void setEndedCourses(List<RunnedCourse> endedCourses) {
		this.endedCourses = endedCourses;
	}


	public void setCanselledCourses(List<RunnedCourse> canselledCourses) {
		this.canselledCourses = canselledCourses;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((canselledCourses == null) ? 0 : canselledCourses.hashCode());
		result = prime * result + ((currentCourses == null) ? 0 : currentCourses.hashCode());
		result = prime * result + ((endedCourses == null) ? 0 : endedCourses.hashCode());
		result = prime * result + ((studentMarks == null) ? 0 : studentMarks.hashCode());
		result = prime * result + ((studentRating == null) ? 0 : studentRating.hashCode());
		result = prime * result + ((userAdress == null) ? 0 : userAdress.hashCode());
		result = prime * result + ((userDateOfBirth == null) ? 0 : userDateOfBirth.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userFaculty == null) ? 0 : userFaculty.hashCode());
		result = prime * result + userFacultyId;
		result = prime * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		result = prime * result + ((userPatronymic == null) ? 0 : userPatronymic.hashCode());
		result = prime * result + ((userPhone == null) ? 0 : userPhone.hashCode());
		result = prime * result + ((userPhotoLink == null) ? 0 : userPhotoLink.hashCode());
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
		result = prime * result + userRoleId;
		result = prime * result + ((userSecondName == null) ? 0 : userSecondName.hashCode());
		result = prime * result + userStatusId;
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
		UserInfo other = (UserInfo) obj;
		if (canselledCourses == null) {
			if (other.canselledCourses != null)
				return false;
		} else if (!canselledCourses.equals(other.canselledCourses))
			return false;
		if (currentCourses == null) {
			if (other.currentCourses != null)
				return false;
		} else if (!currentCourses.equals(other.currentCourses))
			return false;
		if (endedCourses == null) {
			if (other.endedCourses != null)
				return false;
		} else if (!endedCourses.equals(other.endedCourses))
			return false;
		if (studentMarks == null) {
			if (other.studentMarks != null)
				return false;
		} else if (!studentMarks.equals(other.studentMarks))
			return false;
		if (studentRating == null) {
			if (other.studentRating != null)
				return false;
		} else if (!studentRating.equals(other.studentRating))
			return false;
		if (userAdress == null) {
			if (other.userAdress != null)
				return false;
		} else if (!userAdress.equals(other.userAdress))
			return false;
		if (userDateOfBirth == null) {
			if (other.userDateOfBirth != null)
				return false;
		} else if (!userDateOfBirth.equals(other.userDateOfBirth))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userFaculty == null) {
			if (other.userFaculty != null)
				return false;
		} else if (!userFaculty.equals(other.userFaculty))
			return false;
		if (userFacultyId != other.userFacultyId)
			return false;
		if (userFirstName == null) {
			if (other.userFirstName != null)
				return false;
		} else if (!userFirstName.equals(other.userFirstName))
			return false;
		if (userId != other.userId)
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		if (userPatronymic == null) {
			if (other.userPatronymic != null)
				return false;
		} else if (!userPatronymic.equals(other.userPatronymic))
			return false;
		if (userPhone == null) {
			if (other.userPhone != null)
				return false;
		} else if (!userPhone.equals(other.userPhone))
			return false;
		if (userPhotoLink == null) {
			if (other.userPhotoLink != null)
				return false;
		} else if (!userPhotoLink.equals(other.userPhotoLink))
			return false;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		if (userRoleId != other.userRoleId)
			return false;
		if (userSecondName == null) {
			if (other.userSecondName != null)
				return false;
		} else if (!userSecondName.equals(other.userSecondName))
			return false;
		if (userStatusId != other.userStatusId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "UserInfo [userFirstName=" + userFirstName + ", userSecondName=" + userSecondName + ", userPatronymic="
				+ userPatronymic + ", userLogin=" + userLogin + ", userEmail=" + userEmail + ", userRole=" + userRole
				+ ", userFaculty=" + userFaculty + ", userAdress=" + userAdress + ", userPhone=" + userPhone
				+ ", userPhotoLink=" + userPhotoLink + ", studentRating=" + studentRating + ", userDateOfBirth="
				+ userDateOfBirth + ", userId=" + userId + ", userRoleId=" + userRoleId + ", userStatusId="
				+ userStatusId + ", userFacultyId=" + userFacultyId + ", studentMarks=" + studentMarks
				+ ", currentCourses=" + currentCourses + ", endedCourses=" + endedCourses + ", canselledCourses="
				+ canselledCourses + "]";
	}

}
