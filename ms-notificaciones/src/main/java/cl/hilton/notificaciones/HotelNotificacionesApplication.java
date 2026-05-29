package cl.hilton.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.notificaciones.client")
@SpringBootApplication
public class HotelNotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelNotificacionesApplication.class, args);
	}
}