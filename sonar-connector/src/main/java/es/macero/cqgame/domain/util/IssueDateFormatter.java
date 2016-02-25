package es.macero.cqgame.domain.util;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class IssueDateFormatter {
//    private static DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    public static DateTimeFormatter LOCALDATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private IssueDateFormatter() {
    }

//    public static LocalDate format(String s) {
//        return LocalDate.parse(s, f);
//    }
    
    public static LocalDate format(String s) {
        return LOCALDATETIME_FORMATTER.parseLocalDate(s);
    }
}
