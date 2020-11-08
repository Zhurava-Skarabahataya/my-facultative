package by.epamtc.facultative.dao;

import java.util.ResourceBundle;

public class QueryManager {

	private static final QueryManager instance = new QueryManager();
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(SQL_PROPERTIES_FILE);
	
	private static final String SQL_PROPERTIES_FILE = "sql";
	
	private QueryManager() {
		
	}
	
	public static QueryManager getInstance() {
		return instance;
	}
	
	public String getValue (String key) {
		return resourceBundle.getString(key);
	}
	
}
