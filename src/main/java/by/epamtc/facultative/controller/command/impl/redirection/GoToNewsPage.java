package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.News;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.NewsService;
import by.epamtc.facultative.service.ServiceProvider;

public class GoToNewsPage implements Command {
	
	private static final Logger logger = Logger.getLogger(GoToNewsPage.class);
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	
	private final String NEWS_PAGE_PATH = "WEB-INF/jsp/news-page.jsp";
	
	private final String REQUEST_ATTRIBUTE_NEWS = "news";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			List<News> news;
			
			NewsService newsService = ServiceProvider.getInstance().getNewsService();
			news = newsService.findNews();
			
			request.setAttribute(REQUEST_ATTRIBUTE_NEWS, news);
			request.getRequestDispatcher(NEWS_PAGE_PATH).forward(request, response);

		} catch (ServletException | IOException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

		}

	}

}
