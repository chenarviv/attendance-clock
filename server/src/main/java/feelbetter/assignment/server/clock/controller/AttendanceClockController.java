package feelbetter.assignment.server.clock.controller;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.server.clock.global.ReportNotExistException;
import feelbetter.assignment.server.clock.service.IAttendanceClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clock")
public class AttendanceClockController {

    @Autowired
    private IAttendanceClockService attendanceClockService;

    @GetMapping("/{userId}/report")
    private MonthReport getMonthReport(@PathVariable String userId, @RequestParam int month) throws ReportNotExistException {
        return attendanceClockService.getMonthReport(userId, month);
    }

    @PutMapping("/{userId}")
    private void checkInAndOut(@PathVariable String userId){
        attendanceClockService.checkInAndOut(userId);
    }
}
