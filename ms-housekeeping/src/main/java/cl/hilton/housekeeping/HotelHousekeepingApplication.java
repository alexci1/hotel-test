package cl.hilton.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class HotelHousekeepingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelHousekeepingApplication.class, args);
    }
}
