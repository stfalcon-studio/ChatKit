package com.stfalcon.chatkit.features.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Created by troy379 on 09.12.16.
 */
public final class DatesUtils {
    private DatesUtils() {
        throw new AssertionError();
    }

    public static String format(Date date, Template template) {
        if (date == null) return "";
        return new SimpleDateFormat(template.get(), Locale.getDefault())
                .format(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public enum Template {
        STRING_MONTH("dd MMMM yyyy"),
        TIME("HH:mm");

        private String template;

        Template(String template) {
            this.template = template;
        }

        public String get() {
            return template;
        }
    }
}
