package Models.DBModels.suppliers;

public class Date {
    int year;
    int month;
    int day;

    public Date(Date other){
        this.year = other.getYear();
        this.month = other.getMonth();
        this.day = other.getDay();
    }
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(String str){
        String[] strs = str.split("-");
        this.year = Integer.parseInt(strs[0].substring(1));
        this.month = Integer.parseInt(strs[1]);
        this.day = Integer.parseInt(strs[2].substring(0,strs[2].length()-1));
    }

    @Override
    public String toString() {
        return "{" + year + "-" + month + "-" + day + "}";
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}