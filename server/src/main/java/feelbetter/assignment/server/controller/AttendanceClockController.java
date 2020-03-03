package feelbetter.assignment.server.controller;

import feelbetter.assignment.model.MonthReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import feelbetter.assignment.server.service.IAttendanceClockService;

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
