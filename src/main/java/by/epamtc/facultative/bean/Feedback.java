package by.epamtc.facultative.bean;

import java.io.Serializable;

public class Feedback implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int feedbackId;
	int authorId;
	int courseId;
	
	String feedbackText;
	
	public Feedback() {
		
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getFeedbackText() {
		return feedbackText;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result + courseId;
		result = prime * result + feedbackId;
		result = prime * result + ((feedbackText == null) ? 0 : feedbackText.hashCode());
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
		Feedback other = (Feedback) obj;
		if (authorId != other.authorId)
			return false;
		if (courseId != other.courseId)
			return false;
		if (feedbackId != other.feedbackId)
			return false;
		if (feedbackText == null) {
			if (other.feedbackText != null)
				return false;
		} else if (!feedbackText.equals(other.feedbackText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", authorId=" + authorId + ", courseId=" + courseId
				+ ", feedbackText=" + feedbackText + "]";
	}
	
	
	
}
