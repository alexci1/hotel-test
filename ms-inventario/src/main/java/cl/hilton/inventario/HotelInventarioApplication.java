package cl.hilton.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelInventarioApplication.class, args);
	}

}
