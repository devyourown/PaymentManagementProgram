package com.cleansoftware.payment.schedule;

import java.util.Calendar;

public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(Calendar date) {
        return date.get(Calendar.WEEK_OF_MONTH) % 2 == 0;
    }
}
