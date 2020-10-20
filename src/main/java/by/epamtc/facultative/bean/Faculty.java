package by.epamtc.facultative.bean;

public class Faculty {
	
	private String facultyName;
	private String facultyDescription;
	
	private int faculteId;
	private int dean_id;
	
	public Faculty() {
		
	}

	public String getFacultyName() {
		return facultyName;
	}

	public String getFacultyDescription() {
		return facultyDescription;
	}

	public int getFaculteId() {
		return faculteId;
	}

	public int getDean_id() {
		return dean_id;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public void setFacultyDescription(String facultyDescription) {
		this.facultyDescription = facultyDescription;
	}

	public void setFaculteId(int faculteId) {
		this.faculteId = faculteId;
	}

	public void setDean_id(int dean_id) {
		this.dean_id = dean_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dean_id;
		result = prime * result + faculteId;
		result = prime * result + ((facultyDescription == null) ? 0 : facultyDescription.hashCode());
		result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
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
		Faculty other = (Faculty) obj;
		if (dean_id != other.dean_id)
			return false;
		if (faculteId != other.faculteId)
			return false;
		if (facultyDescription == null) {
			if (other.facultyDescription != null)
				return false;
		} else if (!facultyDescription.equals(other.facultyDescription))
			return false;
		if (facultyName == null) {
			if (other.facultyName != null)
				return false;
		} else if (!facultyName.equals(other.facultyName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FacultyInfo [facultyName=" + facultyName + ", facultyDescription=" + facultyDescription + ", faculteId="
				+ faculteId + ", dean_id=" + dean_id + "]";
	}
	
	
}
