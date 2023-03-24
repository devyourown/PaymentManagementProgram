package com.cleansoftware.payment.schedule;

import java.util.Calendar;
import java.util.GregorianCalendar;

public interface PaymentSchedule {
    boolean isPayDate(Calendar date);

    Calendar getStartDate(Calendar payDate);
}
