package DB.implementations.MockFactory;

import Auxiliary.IdGenerator;
import Auxiliary.Enums.Models.AvailabilityStatus;
import Models.DBModels.Employees.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Domain.Employees.*;

public class MockEmployeeData {

    public static List<Employee> createMockEmployees() {
        List<Employee> employees = new ArrayList<>();

        // --- Mock Bank Accounts ---
        BankAccount mockBank1 = new BankAccount("Leumi-Hills", "072543", "972");
        BankAccount mockBank2 = new BankAccount("Hapoalim", "072923", "001");
        BankAccount mockBank3 = new BankAccount("Bank-Yahav", "072577", "625");
        BankAccount mockBank4 = new BankAccount("Bank-Hapoalim", "072589", "311");
        BankAccount mockBank5 = new BankAccount("Discount", "072111", "321");
        BankAccount mockBank6 = new BankAccount("Bank-Yahav", "0725431", "972");

        // --- Mock Contracts ---
        Contract fullTimeContract = new Contract(IdGenerator.getNextIdContract(),7, 14, 21, 160);
        Contract partTimeContract = new Contract(IdGenerator.getNextIdContract(),5, 10, 14, 80);
        Contract flexibleContract = new Contract(IdGenerator.getNextIdContract(), 6, 12, 18, 120);

        // --- Mock Availabilities ---
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

        // --- Mock Roles ---
        ARole cashier1 = new Cashier(IdGenerator.getNextIdCashier());
        ARole manager1 = new Shift_Manager(IdGenerator.getNextIdManager());
        ARole hrLead1 = new HR_Lead(IdGenerator.getNextIdHR());
        ARole cleaner1 = new Cleaner(IdGenerator.getNextIdCleaner());
        ARole cashier2 = new Cashier(IdGenerator.getNextIdCashier());
        ARole security1 = new Security(IdGenerator.getNextIdSecurity());

        // --- Employees ---
        // Employee 1: Alice
        List<ARole> rolesList1 = new ArrayList<>(List.of(cashier1));
        Employee emp1 = new Employee(
                1, "Alice", 32.5, "EMP001",
                LocalDate.of(2022, 3, 15),
                rolesList1,
                mockBank1,
                fullTimeContract,
                mockAvailability1
        );
        employees.add(emp1);

        // Employee 2: Bob
        List<ARole> rolesList2 = new ArrayList<>();
        rolesList2.add(manager1);
        rolesList2.add(cashier2);
        Employee emp2 = new Employee(
                2, "Bob", 47.1, "EMP002",
                LocalDate.of(2023, 1, 10),
                rolesList2,
                mockBank2,
                partTimeContract,
                mockAvailability2
        );
        employees.add(emp2);

        // Employee 3: Charlie
        List<ARole> rolesList3 = new ArrayList<>();
        rolesList3.add(hrLead1);
        Employee emp3 = new Employee(
                3, "Charlie", 120.2, "EMP003",
                LocalDate.of(2021, 5, 20),
                rolesList3,
                mockBank3,
                flexibleContract,
                mockAvailability3
        );
        employees.add(emp3);

        // Employee 4: David
        List<ARole> rolesList4 = new ArrayList<>();
        rolesList4.add(cleaner1);
        Employee emp4 = new Employee(
                4, "David", 35.0, "EMP004",
                LocalDate.of(2023, 6, 1),
                rolesList4,
                mockBank4,
                partTimeContract,
                mockAvailability4
        );
        employees.add(emp4);

        // Employee 5: Eve
        List<ARole> rolesList5 = new ArrayList<>();
        rolesList5.add(security1);
        rolesList5.add(cleaner1);
        Employee emp5 = new Employee(
                5, "Eve", 33.5, "EMP005",
                LocalDate.of(2023, 7, 15),
                rolesList5,
                mockBank5,
                flexibleContract,
                mockAvailability5
        );
        employees.add(emp5);

        // Employee 6: Inna
        List<ARole> rolesList6 = new ArrayList<>();
        rolesList6.add(security1);
        rolesList6.add(cleaner1);
        Employee emp6 = new Employee(
                6, "Inna", 33, "EMP006",
                LocalDate.of(2024, 7, 10),
                rolesList6,
                mockBank6,
                flexibleContract,
                mockAvailability5
        );
        employees.add(emp6);

        return employees;
    }
}