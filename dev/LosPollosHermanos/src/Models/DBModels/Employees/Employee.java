package Models.DBModels.Employees;
import Auxiliary.Enums.Models.AvailabilityStatus;
import Auxiliary.IdGenerator;
import DB.WorkersDB.EmployeesDB;
import Domain.Employees.*;
import Models.DBModels.BaseModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.Session;

@Entity
@Table(name = "Employees")
public class Employee extends BaseModel {
    @Column(nullable = false)
    private double salary;
    @Column(nullable = false , unique = true)
    private String employeeID;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate recruitment_Date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "employee_roles",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<ARole> allRoles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount bank_info;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", nullable = false)
    private Contract employee_condition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability_id", referencedColumnName = "id", nullable = false)
    private Availability availability;


    public Employee(int id, String name, double salary, String employeeId, LocalDate recruitment_Date,
                    List<ARole> roles, BankAccount bank_info, Contract employee_condition, Availability availability) {
        super(id, Employee.class.getName());
        this.salary = salary;
        this.employeeID = employeeId;
        this.name = name;
        this.recruitment_Date = recruitment_Date;
        if (roles != null) {
            this.allRoles.addAll(roles);
        }
        this.bank_info = bank_info;
        this.employee_condition = employee_condition;
        this.availability = availability;
    }

    public Employee() {
        super(0,Employee.class.getName());
    }

    //salary setters and getter will be used in the future !
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getEmployeeID() {
        return employeeID;
    }
    public String getName() {
        return name;
    }
    public List<ARole> getRolesList() {
        return allRoles;
    }
    public Availability getAvailability() {
        return availability;
    }
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
    public List<ARole> getAllRoles() {
        return allRoles;
    }
    public void setAllRoles(List<ARole> allRoles) {
        this.allRoles = allRoles;
    }



    @Override
    public String toString() {
        String strToReturn = "\nEmployee: \n" +
                "Name= " + name + '\n' +
                "EmployeeID= " + employeeID + '\n' +
                "Salary= " + salary + " Shekels per Hour \n" +
                "Recruitment_Date= " + recruitment_Date + '\n' +
                bank_info.toString() + '\n' +
                employee_condition.toString() + '\n'
                + availability.toString()+ '\n' ;

        for (ARole role : allRoles) {
            strToReturn += role.getRole()+ ", ";
        }

        return strToReturn.substring(0, strToReturn.length() - 2);
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        if (!(other instanceof Employee)) return false;
        Employee otherEmployee = (Employee) other;

        this.salary = otherEmployee.salary;
        this.name = otherEmployee.name;
        this.recruitment_Date = otherEmployee.recruitment_Date;

        // Handle bank_info
        if (otherEmployee.bank_info != null) {
            if (this.bank_info == null) {
                this.bank_info = otherEmployee.bank_info;
            } else {
                this.bank_info.copyFromOther(otherEmployee.bank_info, session);
            }
        }

        // Handle employee_condition
        if (otherEmployee.employee_condition != null) {
            if (this.employee_condition == null) {
                this.employee_condition = otherEmployee.employee_condition;
            } else {
                this.employee_condition.copyFromOther(otherEmployee.employee_condition, session);
            }
        }

        // Handle availability
        if (otherEmployee.availability != null) {
            if (this.availability == null) {
                this.availability = otherEmployee.availability;
            } else {
                this.availability.copyFromOther(otherEmployee.availability, session);
            }
        }

        // Handle roles
        if (otherEmployee.allRoles != null) {
            this.allRoles.clear();
            this.allRoles.addAll(otherEmployee.allRoles);
        }

        return true;
    }


    public static void createEmployeesDB() {
        BankAccount mockBank1 = new BankAccount("Leumi-Hills", "072543", "972");
        BankAccount mockBank2 = new BankAccount("Hapoalim", "072923", "001");
        BankAccount mockBank3 = new BankAccount("Bank-Yahav", "072577", "625");
        BankAccount mockBank4 = new BankAccount("Bank-Hapoalim", "072589", "311");
        BankAccount mockBank5 = new BankAccount("Discount", "072111", "321");
        BankAccount mockBank6 = new BankAccount("Bank-Yahav", "0725431", "972");

        Contract fullTimeContract = new Contract(IdGenerator.getNextIdContract(),7, 14, 21, 160);
        Contract partTimeContract = new Contract(IdGenerator.getNextIdContract(),5, 10, 14, 80);
        Contract flexibleContract = new Contract(IdGenerator.getNextIdContract(), 6, 12, 18, 120);

        Availability mockAvailability1 = new Availability(
                AvailabilityStatus.Day, AvailabilityStatus.Day, AvailabilityStatus.Day,
                AvailabilityStatus.Night, AvailabilityStatus.Unavailable
        );

        Availability mockAvailability2 = new Availability(
                AvailabilityStatus.Day, AvailabilityStatus.Night, AvailabilityStatus.Night,
                AvailabilityStatus.Day, AvailabilityStatus.Day
        );

        Availability mockAvailability3 = new Availability(
                AvailabilityStatus.Day, AvailabilityStatus.Night, AvailabilityStatus.Day,
                AvailabilityStatus.Night, AvailabilityStatus.Day
        );

        Availability mockAvailability4 = new Availability(
                AvailabilityStatus.Day, AvailabilityStatus.Day, AvailabilityStatus.Day,
                AvailabilityStatus.Day, AvailabilityStatus.Night
        );

        Availability mockAvailability5 = new Availability(
                AvailabilityStatus.Both, AvailabilityStatus.Both, AvailabilityStatus.Both,
                AvailabilityStatus.Both, AvailabilityStatus.Both
        );

        ARole cashier1 = new Cashier(IdGenerator.getNextIdCashier());
        ARole manager1 = new Shift_Manager(IdGenerator.getNextIdManager());
        ARole hrLead1 = new HR_Lead(IdGenerator.getNextIdHR());
        ARole cleaner1 = new Cleaner(IdGenerator.getNextIdCleaner());
        ARole cashier2 = new Cashier(IdGenerator.getNextIdCashier());
        ARole security1 = new Security(IdGenerator.getNextIdSecurity());
        List<ARole> rolesList = new ArrayList<>(List.of(cashier1));

        Employee emp1 = new Employee(
                1, "Alice", 32.5, "EMP001",
                LocalDate.of(2022, 3, 15),
                new ArrayList<>(rolesList),
                mockBank1,
                fullTimeContract,
                mockAvailability1
        );

        rolesList.clear();
        rolesList.add(manager1);
        rolesList.add(cashier2);

        Employee emp2 = new Employee(
                2, "Bob", 47.1, "EMP002",
                LocalDate.of(2023, 1, 10),
                new ArrayList<>(rolesList),
                mockBank2,
                partTimeContract,
                mockAvailability2
        );

        rolesList.clear();
        rolesList.add(hrLead1);

        Employee emp3 = new Employee(
                3, "Charlie", 120.2, "EMP003",
                LocalDate.of(2021, 5, 20),
                new ArrayList<>(rolesList),
                mockBank3,
                flexibleContract,
                mockAvailability3
        );

        rolesList.clear();
        rolesList.add(cleaner1);

        Employee emp4 = new Employee(
                4, "David", 35.0, "EMP004",
                LocalDate.of(2023, 6, 1),
                new ArrayList<>(rolesList),
                mockBank4,
                partTimeContract,
                mockAvailability4
        );

        rolesList.clear();
        rolesList.add(security1);
        rolesList.add(cleaner1);

        Employee emp5 = new Employee(
                5, "Eve", 33.5, "EMP005",
                LocalDate.of(2023, 7, 15),
                new ArrayList<>(rolesList),
                mockBank5,
                flexibleContract,
                mockAvailability5
        );

        rolesList.clear();
        rolesList.add(security1);
        rolesList.add(cleaner1);

        Employee emp6 = new Employee(
                6, "Inna", 33, "EMP006",
                LocalDate.of(2024, 7, 10),
                new ArrayList<>(rolesList),
                mockBank6,
                flexibleContract,
                mockAvailability5
        );

        EmployeesDB.addEmployeeToDB(emp1);
        EmployeesDB.addEmployeeToDB(emp2);
        EmployeesDB.addEmployeeToDB(emp3);
        EmployeesDB.addEmployeeToDB(emp4);
        EmployeesDB.addEmployeeToDB(emp5);
        EmployeesDB.addEmployeeToDB(emp6);
    }
}
