package cl.hilton.tarifas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelTarifasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelTarifasApplication.class, args);
	}

}
