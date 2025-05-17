package Models.DBModels.Employees;


import Models.DBModels.transport.Branch;

public class Contract {
    private  int pension_Percentage;
    private  int sick_Days;
    private  int vacation_Days;
    private  int minMonthlyHours;

    public Contract(int pension_Percentage, int sick_Days, int vacation_Days, int minMonthlyHours) {
        this.pension_Percentage = pension_Percentage;
        this.sick_Days = sick_Days;
        this.vacation_Days = vacation_Days;
        this.minMonthlyHours = minMonthlyHours;
    }


    @Override
    public String toString() {
        return "Contract Information=\n" +
                "Pension Percentage= " + pension_Percentage + "%" +
                ", Sick Days= " + sick_Days +
                ", Vacation Days= " + vacation_Days +
                ", Min Monthly Hours= " + minMonthlyHours;
    }

}

