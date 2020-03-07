package co.chen.model;

import lombok.Data;

@Data
public class Report {
    private String startTime;
    private String endTime;

    public Report(String startTime) {
        this.startTime = startTime;
    }
}
