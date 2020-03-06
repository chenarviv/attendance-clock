package feelbetter.assignment.server.clock.service.impl;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.model.Report;
import feelbetter.assignment.server.clock.dal.IUserMonthReportsDAL;
import feelbetter.assignment.server.clock.dal.model.UserMonthReport;
import feelbetter.assignment.server.clock.service.IAttendanceClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceClockServiceImpl implements IAttendanceClockService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Jerusalem"));

    @Autowired
    private IUserMonthReportsDAL userMonthReportsDAL;

    @Override
    public MonthReport getMonthReport(String userId, int month) throws Exception {
        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) {
            throw new Exception("Could not find report of month=" + month + " for user id=" + userId);
        }
        return new MonthReport(userMonthReport.getReport());
    }

    @Override
    public void checkInAndOut(String userId) {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonth().getValue();
        String today = LocalDate.now().format(DATE_FORMATTER);
        String currentTime = LocalTime.now().format(TIME_FORMATTER);

        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) { // first time reporting in this month
            userMonthReport = new UserMonthReport();
            userMonthReport.setUserId(userId);
            userMonthReport.setMonth(month);
            List<Report> dayReport = new ArrayList<>();
            Report report = new Report();
            report.setStartTime(currentTime);
            dayReport.add(report);
            userMonthReport.addDayReport(today, dayReport);
        } else {
            List<Report> dayReport = userMonthReport.getReport().get(today);
            if (dayReport == null) {
                dayReport = new ArrayList<>();
                Report report = new Report();
                report.setStartTime(currentTime);
                dayReport.add(report);
                userMonthReport.addDayReport(today, dayReport);
            } else {
                Report lastReport = dayReport.get(dayReport.size() - 1);
                if (lastReport.getStartTime() == null) {
                    lastReport.setStartTime(currentTime);
                } else if (lastReport.getEndTime() == null) {
                    lastReport.setEndTime(currentTime);
                } else {
                    Report newReport = new Report();
                    newReport.setStartTime(currentTime);
                    dayReport.add(newReport);
                }
            }
        }
        userMonthReportsDAL.save(userMonthReport);
    }
}
