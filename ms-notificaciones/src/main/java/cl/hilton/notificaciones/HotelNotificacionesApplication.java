package cl.hilton.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HotelNotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelNotificacionesApplication.class, args);
	}

}
