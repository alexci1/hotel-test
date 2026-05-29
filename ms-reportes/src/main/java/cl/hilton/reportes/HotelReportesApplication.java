package cl.hilton.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages ="cl.hilton.reportes")
@SpringBootApplication
public class HotelReportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReportesApplication.class, args);
	}
}