package by.epamtc.facultative.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import by.epamtc.facultative.bean.News;
import by.epamtc.facultative.service.NewsService;

public class NewsServiceImpl implements NewsService{

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(NEWS_PROPERTIES_FILE);
	
	private static final NewsServiceImpl instance = new NewsServiceImpl();

	private static final String NEWS_PROPERTIES_FILE = "news.news";
	
	private static final String NEWS_AMOUNT = "news_amount";
	private static final String NEWS_TITLE = "news_title_";
	private static final String NEWS_TEXT = "news_text_";
	private static final String NEWS_IMAGE = "news_image_";
	
	private NewsServiceImpl(){
		
	}
	
	public static NewsServiceImpl getInstance() {
		return instance;
	}

	@Override
	public List<News> findNews() {
		
		List <News> newsList = new ArrayList<News>();
		
		int amountOfNews = Integer.parseInt(resourceBundle.getString(NEWS_AMOUNT));
		
		for (int newsCount = 1; newsCount <= amountOfNews; newsCount++) {
			
			String newsTitleKey = NEWS_TITLE + newsCount;
			String newsTextKey = NEWS_TEXT + newsCount;
			String newsImageKey = NEWS_IMAGE + newsCount;
			
			String newsTitle = resourceBundle.getString(newsTitleKey);
			String newsText = resourceBundle.getString(newsTextKey);
			String newsImage = resourceBundle.getString(newsImageKey);
			
			News news = new News();
			news.setTitle(newsTitle);
			news.setText(newsText);
			news.setImagePath(newsImage);
			
			newsList.add(news);
		}
		
		return newsList;
	}

}
