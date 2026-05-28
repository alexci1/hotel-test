package cl.hilton.checkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelCheckinApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelCheckinApplication.class, args);
	}

}
