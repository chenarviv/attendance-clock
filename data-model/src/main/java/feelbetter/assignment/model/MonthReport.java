package feelbetter.assignment.model;

import java.util.List;
import java.util.Map;

public class MonthReport {
    private Map<String, List<Report>> report;

    public MonthReport(Map<String, List<Report>> report) {
        this.report = report;
    }

    public Map<String, List<Report>> getReport() {
        return report;
    }

    public void setReport(Map<String, List<Report>> report) {
        this.report = report;
    }
}
