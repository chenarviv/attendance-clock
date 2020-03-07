package co.chen.server.clock.controller;

import co.chen.model.MonthReport;
import co.chen.model.Report;
import co.chen.server.clock.global.ReportNotExistException;
import co.chen.server.clock.global.TooManyReportsException;
import co.chen.server.clock.service.IAttendanceClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/clock")
public class AttendanceClockController {

    @Autowired
    private IAttendanceClockService attendanceClockService;

    @GetMapping("/{userId}/report")
    private Map<String, List<Report>> getMonthReport(@PathVariable String userId, @RequestParam int month) throws ReportNotExistException {
        return attendanceClockService.getMonthReport(userId, month);
    }

    @PutMapping("/{userId}")
    private void checkInAndOut(@PathVariable String userId) throws TooManyReportsException {
        attendanceClockService.checkInAndOut(userId);
    }
}
