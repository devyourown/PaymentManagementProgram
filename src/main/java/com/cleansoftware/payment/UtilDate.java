package com.cleansoftware.payment;

import com.cleansoftware.pay.Paycheck;

import java.util.Calendar;

public class UtilDate {
    public static boolean isInPayPeriod(Calendar theDate, Paycheck pc) {
        return (pc.getPayPeriodStartDate().before(theDate)) &&
                (theDate.before(pc.getPayPeriodEndDate()) ||
                        theDate.compareTo(pc.getPayPeriodEndDate()) == 0);
    }
}
