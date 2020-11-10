package by.epamtc.facultative.tag;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.impl.localization.ChangeLanguageCommand;

public class DateTag extends TagSupport {

	private static final Logger logger = Logger.getLogger(ChangeLanguageCommand.class);

	private LocalDate date;
	
	private final String DATE_OUTPUT_PATTERN = "dd-MM-yyyy";

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int doStartTag() {

		try {
			if (date != null) {
				JspWriter out = pageContext.getOut();
				out.write(date.format(DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN)));
			}

		} catch (IOException e) {
			logger.error(e);
		}

		return SKIP_BODY;
	}

}
