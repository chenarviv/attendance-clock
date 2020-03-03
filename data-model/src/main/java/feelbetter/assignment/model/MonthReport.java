package feelbetter.assignment.model;

import java.util.ArrayList;
import java.util.List;

public class MonthReport {

    private int month;
    private List<DayReport> reportList = new ArrayList<>();

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DayReport> getReportList() {
        return reportList;
    }

    public void setReportList(List<DayReport> reportList) {
        this.reportList = reportList;
    }
}
