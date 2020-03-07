package co.chen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"co.chen"})
public class AttendanceClockApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceClockApplication.class, args);
    }

}
