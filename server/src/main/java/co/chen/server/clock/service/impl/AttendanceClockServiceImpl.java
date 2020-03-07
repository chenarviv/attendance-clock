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

@Service
public class AttendanceClockServiceImpl implements IAttendanceClockService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final int MAX_REPORTS_PER_DAY = 5;

    @Autowired
    private IUserMonthReportsDAL userMonthReportsDAL;

    @Override
    public MonthReport getMonthReport(String userId, int month) throws ReportNotExistException {
        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) {
            throw new ReportNotExistException("Could not find report of month=" + month + " for user id=" + userId);
        }
        return new MonthReport(userMonthReport.getReport());
    }

    @Override
    public void checkInAndOut(String userId) throws TooManyReportsException {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonth().getValue();
        String today = LocalDate.now().format(DATE_FORMATTER);
        String currentTime = LocalTime.now().format(TIME_FORMATTER);

        UserMonthReport userMonthReport = userMonthReportsDAL.findByUserIdAndMonth(userId, month);
        if (userMonthReport == null) {
            handleFirstDayReportInMonth(userId, month, today, currentTime);
        } else {
            handleDayReportInMonth(today, currentTime, userMonthReport);
        }
    }

    private void handleFirstDayReportInMonth(String userId, int month, String today, String currentTime) {
        UserMonthReport userMonthReport;
        userMonthReport = new UserMonthReport(userId, month);
        List<Report> dayReport = new ArrayList<>();
        dayReport.add(new Report(currentTime));
        userMonthReport.addDayReport(today, dayReport);
        userMonthReportsDAL.save(userMonthReport);
    }

    private void handleDayReportInMonth(String today, String currentTime, UserMonthReport userMonthReport) throws TooManyReportsException {
        List<Report> dayReport = userMonthReport.getReport().get(today);
        if (dayReport == null) {
            dayReport = new ArrayList<>();
            dayReport.add(new Report(currentTime));
            userMonthReport.addDayReport(today, dayReport);
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
