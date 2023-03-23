package com.cleansoftware.database;

import com.cleansoftware.employee.Employee;

import java.util.HashMap;
import java.util.Map;

public class PayrollDatabase {
    private static final PayrollDatabase payrollDatabase = new PayrollDatabase();
    private Map<Integer, Employee> itsEmployee;

    private PayrollDatabase() {
        itsEmployee = new HashMap<>();
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
}
