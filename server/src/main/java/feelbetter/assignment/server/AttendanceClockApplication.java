package feelbetter.assignment.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"feelbetter.assignment"})
public class AttendanceClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceClockApplication.class, args);
    }

}
