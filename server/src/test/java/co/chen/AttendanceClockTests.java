package co.chen;

import co.chen.model.Report;
import co.chen.server.clock.dal.model.UserMonthReport;
import co.chen.server.clock.global.ReportNotExistException;
import co.chen.server.clock.global.TooManyReportsException;
import co.chen.server.clock.service.IAttendanceClockService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestsConfiguration.class)
public class AttendanceClockTests {
    private static final String TEST_USER_ID = "12345";
    private static final String TEST_USER_ID2 = "123456";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Jerusalem"));
    private static final String MONGO_DB_COLLECTION_NAME = "userMonthReports";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IAttendanceClockService attendanceClockService;

    @Before
    public void populateDb() {
        insertTestUserData();
        insertTestUser2Data();
    }

    @After
    public void destroyDb() {
        mongoTemplate.dropCollection(MONGO_DB_COLLECTION_NAME);
        mongoTemplate.createCollection(MONGO_DB_COLLECTION_NAME);
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
            String startTimeString = i < 10 ? String.format("08:0%d", randomMin) : String.format("08:%d", i);
            String endTimeString = i < 10 ? String.format("19:0%d", randomMin) : String.format("19:%d", i);
            LocalTime startTime = LocalTime.parse(startTimeString);
            LocalTime endTime = LocalTime.parse(endTimeString);
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
                    String startTimeString = j < 10 ? String.format("0%d:00", j) : String.format("%d:00", j);
                    String endTimeString = j < 10 ? String.format("0%d:00", j + 1) : String.format("%d:00", j + 1);
                    LocalTime startTime = LocalTime.parse(startTimeString);
                    LocalTime endTime = LocalTime.parse(endTimeString);
                    Report report = new Report(startTime);
                    report.setEndTime(endTime);
                    dayReport.add(report);
                }
            } else {
                int randomMin = r.nextInt(i);
                String startTimeString = i < 10 ? String.format("08:0%d", randomMin) : String.format("08:%d", i);
                String endTimeString = i < 10 ? String.format("19:0%d", randomMin) : String.format("19:%d", i);
                LocalTime startTime = LocalTime.parse(startTimeString);
                LocalTime endTime = LocalTime.parse(endTimeString);
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
        Map<String, List<Report>> monthReport = attendanceClockService.getMonthReport(TEST_USER_ID, 2);
        Assert.assertEquals(29, monthReport.size());
        List<Report> dayReport = monthReport.get("04-02-2020");
        Assert.assertEquals(dayReport.size(), 1);
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNotNull(report.getEndTime());

        Map<String, List<Report>> monthReport2 = attendanceClockService.getMonthReport(TEST_USER_ID2, 3);
        Assert.assertEquals(30, monthReport2.size());
        List<Report> dayReport2 = monthReport2.get("10-03-2020");
        Assert.assertEquals(4, dayReport2.size());
    }

    @Test(expected = ReportNotExistException.class)
    public void testGetReportForNonexistentUserAndMonth() throws ReportNotExistException {
        attendanceClockService.getMonthReport("222", 2);
    }


    @Test(expected = TooManyReportsException.class)
    public void testTooManyReports() throws TooManyReportsException {
        for (int i = 0; i < 12; i++) {
            attendanceClockService.checkInAndOut(TEST_USER_ID);
        }
    }


    @Test
    public void testCheckInAndGetReport() throws ReportNotExistException, TooManyReportsException {
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        Map<String, List<Report>> monthReport = attendanceClockService.getMonthReport(TEST_USER_ID, LocalDate.now().getMonth().getValue());
        List<Report> dayReport = monthReport.get(LocalDate.now().format(DATE_FORMATTER));
        Assert.assertEquals(1, dayReport.size());
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNull(report.getEndTime());
    }

    @Test
    public void testCheckInAndOutAndGetReport() throws ReportNotExistException, TooManyReportsException {
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        attendanceClockService.checkInAndOut(TEST_USER_ID);
        Map<String, List<Report>> monthReport = attendanceClockService.getMonthReport(TEST_USER_ID, LocalDate.now().getMonth().getValue());
        List<Report> dayReport = monthReport.get(LocalDate.now().format(DATE_FORMATTER));
        Assert.assertEquals(1, dayReport.size());
        Report report = dayReport.get(0);
        Assert.assertNotNull(report.getStartTime());
        Assert.assertNotNull(report.getEndTime());
    }
}
