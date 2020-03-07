package co.chen.server.clock.service;

import co.chen.model.MonthReport;
import co.chen.server.clock.global.ReportNotExistException;
import co.chen.server.clock.global.TooManyReportsException;

public interface IAttendanceClockService {
    MonthReport getMonthReport(String userId, int month) throws ReportNotExistException;
    void checkInAndOut(String userId) throws TooManyReportsException;
}
