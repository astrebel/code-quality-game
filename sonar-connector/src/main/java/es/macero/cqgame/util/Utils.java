package es.macero.cqgame.util;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

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
}
