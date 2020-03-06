package feelbetter.assignment.server.clock.service;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.server.clock.global.ReportNotExistException;

public interface IAttendanceClockService {
    MonthReport getMonthReport(String userId, int month) throws ReportNotExistException;
    void checkInAndOut(String userId);
}
