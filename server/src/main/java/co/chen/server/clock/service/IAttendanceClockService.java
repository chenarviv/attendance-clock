package co.chen.server.clock.service;

import co.chen.model.Report;
import co.chen.server.clock.global.ReportNotExistException;
import co.chen.server.clock.global.TooManyReportsException;

import java.util.List;
import java.util.Map;

public interface IAttendanceClockService {
    Map<String, List<Report>> getMonthReport(String userId, int month) throws ReportNotExistException;

    void checkInAndOut(String userId) throws TooManyReportsException;
}
