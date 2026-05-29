package cl.hilton.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.reservas.client")
@SpringBootApplication
public class HotelReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReservasApplication.class, args);
	}
}