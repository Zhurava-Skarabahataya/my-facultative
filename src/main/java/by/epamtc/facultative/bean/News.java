package by.epamtc.facultative.bean;

import java.io.Serializable;

public class News implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String text;
	private String imagePath;
	
	
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		News other = (News) obj;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "News [title=" + title + ", text=" + text + ", imagePath=" + imagePath + "]";
	}
	
	
}
