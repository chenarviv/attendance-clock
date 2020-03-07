package co.chen.server.clock.service.impl;

import co.chen.model.MonthReport;
import co.chen.model.Report;
import co.chen.server.clock.dal.IUserMonthReportsDAL;
import co.chen.server.clock.dal.model.UserMonthReport;
import co.chen.server.clock.global.ReportNotExistException;
import co.chen.server.clock.global.TooManyReportsException;
import co.chen.server.clock.service.IAttendanceClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceClockServiceImpl implements IAttendanceClockService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final int MAX_REPORTS_PER_DAY = 5;

    @Autowired
    private IUserMonthReportsDAL userMonthReportsDAL;

    @Override
    public Map<String, List<Report>> getMonthReport(String userId, int month) throws ReportNotExistException {
        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) {
            throw new ReportNotExistException("Could not find report of month=" + month + " for user id=" + userId);
        }
        return userMonthReport.getReport();
    }

    @Override
    public void checkInAndOut(String userId) throws TooManyReportsException {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonth().getValue();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Jerusalem"));

        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) {
            handleFirstDayReportInMonth(userId, month, today, currentTime);
        } else {
            handleDayReportInMonth(today, currentTime, userMonthReport);
        }
    }

    private void handleFirstDayReportInMonth(String userId, int month, LocalDate today, LocalTime currentTime) {
        UserMonthReport userMonthReport;
        userMonthReport = new UserMonthReport(userId, month);
        List<Report> dayReport = new ArrayList<>();
        dayReport.add(new Report(currentTime));
        userMonthReport.addDayReport(today.format(DATE_FORMATTER), dayReport);
        userMonthReportsDAL.save(userMonthReport);
    }

    private void handleDayReportInMonth(LocalDate today, LocalTime currentTime, UserMonthReport userMonthReport) throws TooManyReportsException {
        List<Report> dayReport = userMonthReport.getReport().get(today.format(DATE_FORMATTER));
        if (dayReport == null) {
            dayReport = new ArrayList<>();
            dayReport.add(new Report(currentTime));
            userMonthReport.addDayReport(today.format(DATE_FORMATTER), dayReport);
        } else {
            Report lastReport = dayReport.get(dayReport.size() - 1);
            if (lastReport.getEndTime() == null) {
                lastReport.setEndTime(currentTime);
            } else {
                if (dayReport.size() == MAX_REPORTS_PER_DAY) {
                    throw new TooManyReportsException("Sorry, you already reported 5 times today");
                }
                dayReport.add(new Report(currentTime));
            }
        }
        userMonthReportsDAL.save(userMonthReport);
    }

}
