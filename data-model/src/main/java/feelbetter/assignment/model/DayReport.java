package feelbetter.assignment.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DayReport {
    private List<Report> reports = new ArrayList<>();
}
