package co.chen.server.clock.dal.model;

import co.chen.model.Report;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document("userMonthReports")
@CompoundIndex(name = "user_month_idx", def = "{'userId' : 1, 'month' : 1}")
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