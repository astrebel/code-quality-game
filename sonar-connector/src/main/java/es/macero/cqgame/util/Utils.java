package es.macero.cqgame.util;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import es.macero.cqgame.domain.util.IssueDateParser;

public final class Utils {

	private Utils() {
	}

	public static Period durationTranslator(String sonarDuration) {
		PeriodFormatter formatter = new PeriodFormatterBuilder()
				.appendDays().appendSuffix("d")
				.appendHours().appendSuffix("h")
				.appendMinutes().appendSuffix("min")
				.toFormatter();

		return formatter.parsePeriod(sonarDuration);
	}
	
	public static boolean withinThisWeek(String closedDateStr) {
		if(closedDateStr != null) {
			LocalDate closedDate = IssueDateParser.parse(closedDateStr);
			LocalDate thisMonday = getTodaysDate().withDayOfWeek(DateTimeConstants.MONDAY);
			
			return closedDate.isAfter(thisMonday);
		}
		
		return false;
	}
	
	private static LocalDate getTodaysDate() {
		return new LocalDate();
	}
}
