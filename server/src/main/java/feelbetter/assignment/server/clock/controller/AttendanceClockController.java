package feelbetter.assignment.server.clock.controller;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.server.clock.service.IAttendanceClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clock")
public class AttendanceClockController {

    @Autowired
    private IAttendanceClockService attendanceClockService;

    @GetMapping("/report")
    private MonthReport getMonthReport(@RequestParam String userId, @RequestParam int month){
        return attendanceClockService.getMonthReport(userId, month);
    }

    @PostMapping("/in")
    private void checkIn(@RequestParam String userId){
        attendanceClockService.checkIn(userId);
    }

    @PostMapping("/out")
    private void checkOut(@RequestParam String userId){
        attendanceClockService.checkOut(userId);
    }

}
