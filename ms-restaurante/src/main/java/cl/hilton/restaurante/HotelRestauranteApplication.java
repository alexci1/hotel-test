package cl.hilton.restaurante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class HotelRestauranteApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRestauranteApplication.class, args);
	}

}
