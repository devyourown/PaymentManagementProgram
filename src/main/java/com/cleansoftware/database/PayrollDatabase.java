package com.cleansoftware.database;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;

import java.util.HashMap;
import java.util.Map;

public class PayrollDatabase {
    private static final PayrollDatabase payrollDatabase = new PayrollDatabase();
    private Map<Integer, Employee> itsEmployee;
    private Map<Integer, Employee> unionMember;

    private PayrollDatabase() {
        itsEmployee = new HashMap<>();
        unionMember = new HashMap<>();
    }

    public static PayrollDatabase getInstance() {
        return payrollDatabase;
    }
    public Employee getEmployee(int empId) {
        return itsEmployee.get(empId);
    }

    public void addEmployee(int empId, Employee employee) {
        itsEmployee.put(empId, employee);
    }

    public void deleteEmployee(int empId) {
        itsEmployee.remove(empId);
    }

    public void clear() {
        itsEmployee.clear();
    }

    public Employee getUnionMember(int memberId) {
        return unionMember.get(memberId);
    }

    public void addUnionMember(int memberId, Employee e) {
        unionMember.put(memberId, e);
    }

    public void removeUnionMember(int memberId) {
        unionMember.remove(memberId);
    }
}
