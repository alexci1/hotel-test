package cl.hilton.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.huespedes")
@SpringBootApplication
public class HotelHuespedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelHuespedesApplication.class, args);
	}
}