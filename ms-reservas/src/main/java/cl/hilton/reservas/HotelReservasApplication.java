package cl.hilton.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReservasApplication.class, args);
	}

}
