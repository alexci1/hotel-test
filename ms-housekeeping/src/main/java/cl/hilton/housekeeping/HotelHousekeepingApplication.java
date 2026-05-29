package cl.hilton.housekeeping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelHousekeepingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelHousekeepingApplication.class, args);
	}
}