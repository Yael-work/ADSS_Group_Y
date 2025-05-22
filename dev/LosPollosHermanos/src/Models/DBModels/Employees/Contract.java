package Models.DBModels.Employees;


import Models.DBModels.BaseModel;
import Models.DBModels.transport.Branch;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.Session;

@Entity
@Table(name = "Contracts")
public class Contract extends BaseModel {
    @Column(nullable = false)
    private  int pension_Percentage;
    @Column(nullable = false)
    private  int sick_Days;
    @Column(nullable = false)
    private  int vacation_Days;
    @Column(nullable = false)
    private  int minMonthlyHours;


    //TODO: getters and setters???

    public Contract(int id, int pension_Percentage, int sick_Days, int vacation_Days, int minMonthlyHours) {
        super(id,Contract.class.getName());
        this.pension_Percentage = pension_Percentage;
        this.sick_Days = sick_Days;
        this.vacation_Days = vacation_Days;
        this.minMonthlyHours = minMonthlyHours;
    }

    public Contract() {
        super(0,Contract.class.getName());
    }


    @Override
    public String toString() {
        return "Contract Information=\n" +
                "Pension Percentage= " + pension_Percentage + "%" +
                ", Sick Days= " + sick_Days +
                ", Vacation Days= " + vacation_Days +
                ", Min Monthly Hours= " + minMonthlyHours;
    }

    @Override
    public boolean copyFromOther(BaseModel other, Session session) {
        return false;
    }

}

