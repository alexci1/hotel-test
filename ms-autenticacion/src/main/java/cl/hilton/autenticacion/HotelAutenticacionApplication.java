package cl.hilton.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.autenticacion")
@SpringBootApplication
public class HotelAutenticacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAutenticacionApplication.class, args);
	}
}
