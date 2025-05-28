package DB.implementations.MockFactory;

import Auxiliary.IdGenerator;
import Domain.Employees.AvailabilityManager;
import Domain.Employees.UpcomingShiftsManagerCLS;
import Models.DBModels.Employees.Employee;
import Models.DBModels.Employees.Shift;

import java.time.LocalDate;
import java.util.*;

public class MockShiftsData {

    public static List<Shift> createUpcomingShiftsMockData() {
        List<Shift> shifts = new ArrayList<>();
        List<Employee> employees = MockEmployeeData.createMockEmployees();
        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee e : employees) {
            employeeMap.put(e.getEmployeeID(), e);
        }

        // --- PAST SHIFT: 3 years ago ---
        {
            List<Employee> available = new ArrayList<>();
            Map<Employee, String> assigned = new HashMap<>();
            LocalDate date = LocalDate.now().minusYears(3).withMonth(5).withDayOfMonth(15);
            String type = "Day";

            Employee emp001 = employeeMap.get("EMP001");
            Employee emp002 = employeeMap.get("EMP002");
            if (emp001 != null) available.add(emp001);
            if (emp002 != null) available.add(emp002);

            assigned.put(emp001, getFirstRoleOrUnknown(emp001));
            assigned.put(emp002, getFirstRoleOrUnknown(emp002));
            available.remove(emp001);
            available.remove(emp002);

            shifts.add(new Shift(
                    IdGenerator.getNextIdShift(), date, type, emp001, assigned, available, null
            ));
        }

        // --- PAST SHIFT: 8 years ago ---
        {
            List<Employee> available = new ArrayList<>();
            Map<Employee, String> assigned = new HashMap<>();
            LocalDate date = LocalDate.now().minusYears(8).withMonth(8).withDayOfMonth(12);
            String type = "Night";

            Employee emp003 = employeeMap.get("EMP003");
            Employee emp004 = employeeMap.get("EMP004");
            if (emp003 != null) available.add(emp003);
            if (emp004 != null) available.add(emp004);

            assigned.put(emp003, getFirstRoleOrUnknown(emp003));
            assigned.put(emp004, getFirstRoleOrUnknown(emp004));
            available.remove(emp003);
            available.remove(emp004);

            shifts.add(new Shift(
                    IdGenerator.getNextIdShift(), date, type, emp003, assigned, available, null
            ));
        }

        // --- FUTURE SHIFT 1
        {
            List<Employee> available = new ArrayList<>();
            Map<Employee, String> assigned = new HashMap<>();
            LocalDate date = LocalDate.of(2025, 5, 22);
            String type = "Day";

            Employee emp002 = employeeMap.get("EMP002");
            Employee emp003 = employeeMap.get("EMP003");
            Employee emp005 = employeeMap.get("EMP005");

            if (isEmployeeAvailableForShift(emp002, date, type)) available.add(emp002);
            if (isEmployeeAvailableForShift(emp003, date, type)) available.add(emp003);
            if (isEmployeeAvailableForShift(emp005, date, type)) available.add(emp005);

            String role002 = getFirstRoleOrUnknown(emp002);
            String role005 = getFirstRoleOrUnknown(emp005);

            if (isEmployeeAvailableForShift(emp002, date, type)) {
                assigned.put(emp002, role002);
                available.remove(emp002);
            }
            if (isEmployeeAvailableForShift(emp005, date, type)) {
                assigned.put(emp005, role005);
                available.remove(emp005);
            }

            shifts.add(new Shift(
                    IdGenerator.getNextIdShift(), date, type, emp002, assigned, available, null
            ));
        }

        // --- FUTURE SHIFT 2 ---
        {
            List<Employee> available = new ArrayList<>();
            Map<Employee, String> assigned = new HashMap<>();
            LocalDate date = LocalDate.of(2025, 5, 21);
            String type = "Night";

            Employee emp002 = employeeMap.get("EMP002");
            Employee emp004 = employeeMap.get("EMP004");
            Employee emp005 = employeeMap.get("EMP005");

            if (isEmployeeAvailableForShift(emp002, date, type)) available.add(emp002);
            if (isEmployeeAvailableForShift(emp004, date, type)) available.add(emp004);
            if (isEmployeeAvailableForShift(emp005, date, type)) available.add(emp005);

            String role002 = getFirstRoleOrUnknown(emp002);
            String role004 = getFirstRoleOrUnknown(emp004);

            if (isEmployeeAvailableForShift(emp002, date, type)) {
                assigned.put(emp002, role002);
                available.remove(emp002);
            }
            if (isEmployeeAvailableForShift(emp004, date, type)) {
                assigned.put(emp004, role004);
                available.remove(emp004);
            }

            shifts.add(new Shift(
                    IdGenerator.getNextIdShift(), date, type, emp002, assigned, available, null
            ));
        }

        // --- FUTURE SHIFT 3  ---
        {
            List<Employee> available = new ArrayList<>();
            Map<Employee, String> assigned = new HashMap<>();
            LocalDate date = LocalDate.of(2025, 5, 20);
            String type = "Day";

            Employee emp001 = employeeMap.get("EMP001");
            Employee emp002 = employeeMap.get("EMP002");
            Employee emp003 = employeeMap.get("EMP003");
            Employee emp004 = employeeMap.get("EMP004");

            if (isEmployeeAvailableForShift(emp001, date, type)) available.add(emp001);
            if (isEmployeeAvailableForShift(emp002, date, type)) available.add(emp002);
            if (isEmployeeAvailableForShift(emp003, date, type)) available.add(emp003);
            if (isEmployeeAvailableForShift(emp004, date, type)) available.add(emp004);

            String role002 = getFirstRoleOrUnknown(emp002);
            String role001 = getFirstRoleOrUnknown(emp001);

            if (isEmployeeAvailableForShift(emp002, date, type)) {
                assigned.put(emp002, role002);
                available.remove(emp002);
            }
            if (isEmployeeAvailableForShift(emp001, date, type)) {
                assigned.put(emp001, role001);
                available.remove(emp001);
            }

            shifts.add(new Shift(
                    IdGenerator.getNextIdShift(), date, type, emp002, assigned, available, null
            ));
        }

        return shifts;
    }

// Dummy: always returns true for demo, replace with your business logic!
//    private static boolean isEmployeeAvailableForShift(Employee emp, LocalDate date, String shiftType) {
//        return emp != null;
//    }

    private static boolean isEmployeeAvailableForShift(Employee emp, LocalDate date, String shiftType) {
        return UpcomingShiftsManagerCLS.isEmployeeAvailableForShift(emp, date, shiftType);//TODO imported domain logic in order to call this method
        //TODO moreover changed the "isEmployeeAvailableForShift" method in UpcomingShiftsManagerCLS to public so it will work, check if needed.
    }

    private static String getFirstRoleOrUnknown(Employee emp) {
        if (emp == null || emp.getRolesList().isEmpty()) return "Unknown";
        return emp.getRolesList().get(0).getRole();
    }
}