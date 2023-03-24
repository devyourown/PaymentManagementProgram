package com.cleansoftware.payment.classification;

import java.util.Calendar;

public class DayUtil {
    public static int getDaysAfterAD(Calendar date) {
        return date.get(Calendar.DAY_OF_YEAR) + 365 * date.get(Calendar.YEAR);
    }
}
