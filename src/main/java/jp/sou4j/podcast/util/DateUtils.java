package jp.sou4j.podcast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static Date parse(String dateString) throws ParseException {
		SimpleDateFormat[] formats = {
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm: Z", Locale.ENGLISH),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.ENGLISH)
		};

		ParseException exception = null;
		for(SimpleDateFormat format : formats) {
			try {
				return format.parse(dateString);
			}
			catch(ParseException e) {
				exception = e;
				continue;
			}
		}

		throw exception;
	}
}
