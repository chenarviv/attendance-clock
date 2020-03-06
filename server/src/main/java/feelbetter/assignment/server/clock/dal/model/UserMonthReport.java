package feelbetter.assignment.server.clock.dal.model;

import feelbetter.assignment.model.Report;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document("userMonthReports")
@Data
public class UserMonthReport {

    @Id
    private String id;
    private String userId;
    private int month;
    private Map<String, List<Report>> report = new HashMap<>();

    public UserMonthReport(String userId, int month) {
        this.userId = userId;
        this.month = month;
    }

    public void addDayReport(String date, List<Report> dayReport) {
        report.put(date, dayReport);
    }

    public Map<String, List<Report>> getReport() {
        return report;
    }

}