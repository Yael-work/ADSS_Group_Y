package Domain.Employees;

import Auxiliary.Enums.Models.AvailabilityStatus;
import Auxiliary.IdGenerator;
import Models.DBModels.BaseModel;
import Models.DBModels.Employees.*;
import Auxiliary.Enums.Domain.Status;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class HR_Lead extends ARole {
    public HR_Lead(int id) {
        super(id, Stocker.class.getName());
        this.roleName = "HR Lead";
    }

    public static Status Terminate_Employee_from_System(String employeeID)
    {
        return EmployeeManagerCLS.deleteEmployee(employeeID);
    }

    public static Status Add_Employee_to_System(String employeeName, double employeeSalary, String employeeID, LocalDate recruitment_Date, List<ARole> rolesList, BankAccount employeeAccount, Contract employeeContract)
    {
        Availability employeeAvailability = new Availability(
                AvailabilityStatus.Unavailable,
                AvailabilityStatus.Unavailable,
                AvailabilityStatus.Unavailable,
                AvailabilityStatus.Unavailable,
                AvailabilityStatus.Unavailable);

        Employee employee = new Employee(
                IdGenerator.getNextIdEmployee(),
                employeeName,
                employeeSalary,
                employeeID,
                recruitment_Date,
                rolesList,
                employeeAccount,
                employeeContract,
                employeeAvailability
        );

        return EmployeeManagerCLS.addEmployee(employee);
    }

    public static Status Set_Shift_Hours(String shiftType, LocalTime startHour, LocalTime endHour) {

        if (shiftType.equals("Day")) {
            Shift.setStartHourDay(startHour);
            Shift.setEndHourDay(endHour);
        }
        else {
            Shift.setStartHourNight(startHour);
            Shift.setEndHourNight(endHour);
        }
        return Status.Success;
    }

    public static void Update_Required_Role_to_Shift(int action ,int roleAmount, String roleName) {
        if (action == 1) {
            UpcomingShiftsManagerCLS.addRequiredRoles(roleName, roleAmount);
        } else if (action == 2) {
            UpcomingShiftsManagerCLS.removeRequiredRole(roleName);
        }
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }
}
