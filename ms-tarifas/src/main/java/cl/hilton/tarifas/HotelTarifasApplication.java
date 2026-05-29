package cl.hilton.tarifas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.tarifas")
@SpringBootApplication
public class HotelTarifasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelTarifasApplication.class, args);
	}
}