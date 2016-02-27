package es.macero.cqgame.domain.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class IssueDateParser {
    public static DateTimeFormatter LOCALDATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private IssueDateParser() {
    }
    
    public static LocalDate parse(String s) {
        return LOCALDATETIME_FORMATTER.parseLocalDate(s);
    }
}
