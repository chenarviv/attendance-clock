package feelbetter.assignment;

import feelbetter.assignment.model.MonthReport;
import feelbetter.assignment.model.Report;
import feelbetter.assignment.server.clock.dal.model.UserMonthReport;
import feelbetter.assignment.server.clock.global.ReportNotExistException;
import feelbetter.assignment.server.clock.service.IAttendanceClockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestsConfiguration.class)
public class AttendanceClockTests {
    private static final String TEST_USER_ID = "12345";
    private static final String TEST_USER_ID2 = "123456";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Jerusalem"));

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IAttendanceClockService attendanceClockService;

    @Before
    public void populateDb() {
        insertTestUserData();
        insertTestUser2Data();
    }

    private void insertTestUserData() {
        UserMonthReport userMonthReport1 = new UserMonthReport(TEST_USER_ID, 1);
        UserMonthReport userMonthReport2 = new UserMonthReport(TEST_USER_ID, 2);

        Map<String, List<Report>> monthReport1 = new HashMap<>();
        Map<String, List<Report>> monthReport2 = new HashMap<>();
        Random r = new Random();

        for (int i = 1; i < 30; i++) {
            List<Report> dayReport1 = new ArrayList<>();
            List<Report> dayReport2 = new ArrayList<>();
            int randomMin = r.nextInt(i);
            String startTime = i < 10 ? String.format("08:0%d", randomMin) : String.format("08:%d", randomMin);
            String endTime = i < 10 ? String.format("19:0%d", randomMin) : String.format("19:%d", randomMin);
            Report report = new Report(startTime);
            report.setEndTime(endTime);
            dayReport1.add(report);
            dayReport2.add(report);
            String date1 = i < 10 ? String.format("0%d-01-2020", i) : String.format("%d-01-2020", i);
            String date2 = i < 10 ? String.format("0%d-02-2020", i) : String.format("%d-02-2020", i);
            monthReport1.put(date1, dayReport1);
            monthReport2.put(date2, dayReport2);
        }

        userMonthReport1.setReport(monthReport1);
        userMonthReport2.setReport(monthReport2);

        mongoTemplate.save(userMonthReport1);
        mongoTemplate.save(userMonthReport2);
    }

    private void insertTestUser2Data() {
        UserMonthReport userMonthReport = new UserMonthReport(TEST_USER_ID2, 3);
        Map<String, List<Report>> monthReport = new HashMap<>();
        Random r = new Random();

        for (int i = 1; i < 31; i++) {
            List<Report> dayReport = new ArrayList<>();
            if (i == 10) {
                for (int j = 8; j < 15; j += 2) {
                    String startTime = j < 10 ? String.format("0%d:00", j) : String.format("%d:00", j);
                    String endTime = j < 10 ? String.format("0%d:00", j + 1) : String.format("%d:00", j + 1);
                    Report report = new Report(startTime);
                    report.setEndTime(endTime);
                    dayReport.add(report);
                }
            } else {
                int randomMin = r.nextInt(i);
                String startTime = i < 10 ? String.format("08:0%d", randomMin) : String.format("08:%d", randomMin);
                String endTime = i < 10 ? String.format("19:0%d", randomMin) : String.format("19:%d", randomMin);
                Report report = new Report(startTime);
                report.setEndTime(endTime);
                dayReport.add(report);
            }
            String date = i < 10 ? String.format("0%d-03-2020", i) : String.format("%d-03-2020", i);
            monthReport.put(date, dayReport);
        }
        userMonthReport.setReport(monthReport);
        mongoTemplate.save(userMonthReport);
    }


    @Test
    public void testGetReport() throws ReportNotExistException {
        MonthReport monthReport1 = attendanceClockService.getMonthReport(TEST_USER_ID, 2);
        Map<String, List<Report>> daysReports = monthReport1.getReport();
        Assert.assertEquals(29, daysReports.size());
        List<Report> dayReport = daysReports.get("04-02-2020");
        Assert.assertEquals(dayReport.size(), 1);
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNotNull(report.getEndTime());

        MonthReport monthReport2 = attendanceClockService.getMonthReport(TEST_USER_ID2, 3);
        Assert.assertNotNull(monthReport2);
        Map<String, List<Report>> daysReports2 = monthReport2.getReport();
        Assert.assertEquals(30, daysReports2.size());
        List<Report> dayReport2 = daysReports2.get("10-03-2020");
        Assert.assertEquals(4, dayReport2.size());
    }

    @Test(expected = ReportNotExistException.class)
    public void testGetReportForNonexistentUserAndMonth() throws ReportNotExistException {
        attendanceClockService.getMonthReport("222", 2);
    }


    @Test
    public void testCheckInAndGetReport() throws ReportNotExistException {
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        MonthReport monthReport = attendanceClockService.getMonthReport(TEST_USER_ID, LocalDate.now().getMonth().getValue());
        Assert.assertNotNull(monthReport);
        Map<String, List<Report>> daysReports = monthReport.getReport();
        List<Report> dayReport = daysReports.get(LocalDate.now().format(DATE_FORMATTER));
        Assert.assertEquals(1, dayReport.size());
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNull(report.getEndTime());
    }

    @Test
    public void testCheckInAndOutAndGetReport() throws ReportNotExistException {
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        MonthReport monthReport = attendanceClockService.getMonthReport(TEST_USER_ID, LocalDate.now().getMonth().getValue());
        Assert.assertNotNull(monthReport);
        Map<String, List<Report>> daysReports = monthReport.getReport();
        List<Report> dayReport = daysReports.get(LocalDate.now().format(DATE_FORMATTER));
        Assert.assertEquals(1, dayReport.size());
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNotNull(report.getEndTime());
    }
}
