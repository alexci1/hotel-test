package cl.hilton.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.pagos")
@SpringBootApplication
public class HotelPagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelPagosApplication.class, args);
	}
}