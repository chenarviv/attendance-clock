package feelbetter.assignment.server.service.impl;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.server.service.IAttendanceClockService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttendanceClockServiceImpl implements IAttendanceClockService {

    @Override
    public MonthReport getMonthReport(String userId, int month) {

        return null;
    }

    @Override
    public void checkIn(String userId) {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonth().getValue();

        getMonthFromDb();

    }

    @Override
    public void checkOut(String userId) {

    }
}
