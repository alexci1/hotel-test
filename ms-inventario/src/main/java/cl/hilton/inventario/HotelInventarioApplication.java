package cl.hilton.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.inventario.client")
@SpringBootApplication
public class HotelInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelInventarioApplication.class, args);
	}
}