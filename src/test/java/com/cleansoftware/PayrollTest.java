package com.cleansoftware;

import com.cleansoftware.change.affiliation.ChangeMemberTransaction;
import com.cleansoftware.change.affiliation.ChangeUnaffiliatedTransaction;
import com.cleansoftware.change.method.ChangeDirectTransaction;
import com.cleansoftware.change.method.ChangeHoldTransaction;
import com.cleansoftware.change.method.ChangeMailTransaction;
import com.cleansoftware.change.classification.ChangeCommissionedTransaction;
import com.cleansoftware.change.classification.ChangeHourlyTransaction;
import com.cleansoftware.change.classification.ChangeSalariedTransaction;
import com.cleansoftware.change.personal.ChangeAddressTransaction;
import com.cleansoftware.change.personal.ChangeNameTransaction;
import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.AddCommissionedEmployee;
import com.cleansoftware.employee.AddHourlyEmployee;
import com.cleansoftware.employee.AddSalariedEmployee;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.pay.PaydayTransaction;
import com.cleansoftware.payment.affiliation.NoAffiliation;
import com.cleansoftware.payment.affiliation.ServiceCharge;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.classification.SalariedClassification;
import com.cleansoftware.payment.method.DirectMethod;
import com.cleansoftware.payment.method.HoldMethod;
import com.cleansoftware.payment.method.MailMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.payment.receipt.SalesReceipt;
import com.cleansoftware.payment.schedule.BiweeklySchedule;
import com.cleansoftware.payment.schedule.MonthlySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.schedule.WeeklySchedule;
import com.cleansoftware.payment.timecard.TimeCard;
import com.cleansoftware.transaction.employee.DeleteEmployeeTransaction;
import com.cleansoftware.transaction.receipt.SalesReceiptTransaction;
import com.cleansoftware.transaction.affilication.ServiceChargeTransaction;
import com.cleansoftware.transaction.timecard.TimeCardTransaction;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    @Test
    void testAddSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(SalariedClassification.class, pc);
        SalariedClassification sc = (SalariedClassification) pc;

        assertEquals(1000.0, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testAddHourlyEmployee() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(HourlyClassification.class, pc);
        HourlyClassification sc = (HourlyClassification) pc;

        assertEquals(1000.0, sc.getHourlyRate(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ms = (WeeklySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testAddCommissionedEmployee() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 10.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(CommissionedClassification.class, pc);
        CommissionedClassification cc = (CommissionedClassification) pc;

        assertEquals(1000.0, cc.getSalary(), 0.001);
        assertEquals(10.0, cc.getCommissionRate(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        BiweeklySchedule ms = (BiweeklySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testDeleteEmployee() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home",
                2500, 3.2);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId);
        dt.execute();
        e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNull(e);
    }

    @Test
    void testTimeCardTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar date = new GregorianCalendar(2011, Calendar.MARCH, 1);
        TimeCardTransaction tct = new TimeCardTransaction(date, 8.0, empId);
        tct.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(2011031);
        assertNotNull(tc);
        assertEquals(8.0, tc.getHours());
    }

    @Test
    void testSalesReceiptTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "hong", "Home",
                1000.0, 0.03);
        t.execute();
        Calendar date = new GregorianCalendar(2001, Calendar.OCTOBER, 31);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(date, 110.0, empId);
        srt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        List<SalesReceipt> sr = cc.getSalesReceipt(date);
        assertNotNull(sr.get(0));
        assertEquals(110.0, sr.get(0).getAmount());
    }

    @Test
    void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        int memberId = 86;
        UnionAffiliation af = new UnionAffiliation(memberId, 12.5);
        e.setAffiliation(af);
        PayrollDatabase.getInstance().addUnionMember(memberId, e);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, 20011101, 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(20011101);
        assertNotNull(sc);
        assertEquals(12.95, sc.getAmount(), 0.01);
    }

    @Test
    void testChangeNameTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob");
        cnt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        assertEquals("Bob", e.getItsName());
    }

    @Test
    void testChangeAddressTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "remote",
                1000.0);
        t.execute();
        ChangeAddressTransaction cat = new ChangeAddressTransaction(empId, "Home");
        cat.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        assertEquals("Home", e.getItsAddress());
    }

    @Test
    void testChangeHourlyTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home",
                2500, 3.2);
        t.execute();
        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.53);
        cht.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        assertEquals(27.53, hc.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        assertNotNull(ws);
    }

    @Test
    void testChangeSalariedTransaction() {
        int empId = 4;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                100000.0, .32);
        t.execute();
        ChangeSalariedTransaction cst = new ChangeSalariedTransaction(empId, 1000.0);
        cst.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        SalariedClassification sc = (SalariedClassification) pc;
        assertNotNull(sc);
        assertEquals(1000, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);
    }

    @Test
    void testChangeCommissionedTransaction() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                90000);
        t.execute();
        ChangeCommissionedTransaction cct = new ChangeCommissionedTransaction(empId, 10000.0, 9.0);
        cct.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        assertEquals(10000.0, cc.getSalary(), 0.001);
        assertEquals(9.0, cc.getCommissionRate(), 0.001);
    }

    @Test
    void testChangeDirectTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                1000.0);
        t.execute();
        ChangeDirectTransaction cdt = new ChangeDirectTransaction(empId);
        cdt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        DirectMethod dm = (DirectMethod) pm;
        assertNotNull(dm);
    }

    @Test
    void testChangeMailTransaction() {
        int empId = 10;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        ChangeMailTransaction cmt = new ChangeMailTransaction(empId);
        cmt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        MailMethod dm = (MailMethod) pm;
        assertNotNull(dm);
    }

    @Test
    void testChangeHoldTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        ChangeMailTransaction cmt = new ChangeMailTransaction(empId);
        cmt.execute();
        assertInstanceOf(MailMethod.class, e.getPaymentMethod());
        ChangeHoldTransaction cht = new ChangeHoldTransaction(empId);
        cht.execute();
        assertInstanceOf(HoldMethod.class, e.getPaymentMethod());
    }

    @Test
    void testChangeMemberTransaction() {
        int empId = 2;
        int memberId = 7734;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 10.2);
        cmt.execute();
        assertInstanceOf(UnionAffiliation.class, e.getAffiliation());
        UnionAffiliation ua = (UnionAffiliation) e.getAffiliation();
        assertEquals(10.2, ua.getDues(), 0.001);
        Employee member = PayrollDatabase.getInstance().getUnionMember(memberId);
        assertEquals(e, member);
    }

    @Test
    void testChangeUnaffiliatedTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        ChangeUnaffiliatedTransaction cut = new ChangeUnaffiliatedTransaction(empId);
        cut.execute();
        assertInstanceOf(NoAffiliation.class, e.getAffiliation());
    }

    @Test
    void testPaySingleSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                1000.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, 10, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals(1000.0, pc.getGrossPay(), .001);
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.getDeductions(), 0.001);
        assertEquals(1000.0, pc.getNetPay(), 0.001);
    }

    @Test
    void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                1000.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, 11, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    void validateHourlyPaycheck(PaydayTransaction pt, int empId, Calendar payDate,
                                double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals(pay, pc.getGrossPay(), .001);
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.getDeductions(), .001);
        assertEquals(pay, pc.getNetPay(), .001);
    }

    @Test
    void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 0.0);
    }

    @Test
    void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        TimeCardTransaction tt = new TimeCardTransaction(payDate, 2.0, empId);
        tt.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 30.5);
    }

    @Test
    void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        TimeCardTransaction tc = new TimeCardTransaction(payDate, 9.0, empId);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
    }

    @Test
    void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 8);
        TimeCardTransaction tt = new TimeCardTransaction(payDate, 9.0, empId);
        tt.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);

        TimeCardTransaction tt = new TimeCardTransaction(payDate, 2.0, empId);
        tt.execute();
        payDate.set(Calendar.DAY_OF_MONTH, 8);
        TimeCardTransaction tt2 = new TimeCardTransaction(payDate, 5.0, empId);
        tt2.execute();
        payDate.set(Calendar.DAY_OF_MONTH, 9);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 7 * 15.25);
    }

    @Test
    void testSingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        Calendar dateInPreviousPayPeriod = new GregorianCalendar(2001, Calendar.NOVEMBER, 2);

        TimeCardTransaction tt = new TimeCardTransaction(payDate, 2.0, empId);
        tt.execute();
        TimeCardTransaction tc2 = new TimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 2 * 15.25);
    }

    void validateCommissionPaycheck(PaydayTransaction pt, int empId, Calendar payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals(pay, pc.getGrossPay(), .001);
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.getDeductions(), .001);
        assertEquals(pay, pc.getNetPay(), .001);
    }

    @Test
    void testPaySingleCommissionedEmployeeOneReceipt() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 3.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 65, empId);
        srt.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateCommissionPaycheck(pt, empId, payDate, 1000 + (65*3.0*0.01));
    }

    @Test
    void testPaySingleCommissionedEmployeeNoReceipt() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 3.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateCommissionPaycheck(pt, empId, payDate, 1000);
    }

    @Test
    void testPaySingleCommissionedEmployeeOnWrongDate() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 3.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    void testPaySingleCommissionedEmployeeTwoReceipt() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 3.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 65, empId);
        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(payDate, 55, empId);
        srt.execute();
        srt2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateCommissionPaycheck(pt, empId, payDate, 1000 + (65*3.0*0.01) + (55*3.0*0.01));
    }

    @Test
    void testPaySingleCommissionedEmployeeWithReceiptsSpanningTwoPayPeriods() {
        int empId = 2;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home", 1000,
                3.0);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 9);
        Calendar dateAfterPayPeriod = new GregorianCalendar(2001, Calendar.NOVEMBER, 23);

        SalesReceiptTransaction srt = new SalesReceiptTransaction(payDate, 200, empId);
        srt.execute();
        SalesReceiptTransaction srt2 = new SalesReceiptTransaction(dateAfterPayPeriod, 100, empId);
        srt2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validateHourlyPaycheck(pt, empId, payDate, 1000 + (200*3.0*0.01));
    }

    @Test
    void testSalariedUnionMemberDues() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();
        int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Calendar payDate = new GregorianCalendar(2001, Calendar.NOVEMBER, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 1000.0);
    }

    private void validatePaycheck(PaydayTransaction pt, int empId, Calendar payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals(pay, pc.getGrossPay(), .001);
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(9.42 * 5, pc.getDeductions(), .001);
        assertEquals(pay-(9.42*5), pc.getNetPay(), .001);
    }
}