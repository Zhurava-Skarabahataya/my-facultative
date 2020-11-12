package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.util.List;

public class InfoForWelcomePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private List <News> news;

	public InfoForWelcomePage() {
		super();
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((news == null) ? 0 : news.hashCode());
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
		if (news == null) {
			if (other.news != null)
				return false;
		} else if (!news.equals(other.news))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoForWelcomePage [news=" + news + "]";
	}

	
	

}
