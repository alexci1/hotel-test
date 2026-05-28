package cl.hilton.notificaciones.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-huespedes")
public interface HuespedClient {

    @GetMapping("/huespedes/email/{email}")
    Map<String, Object> buscarPorEmail(@PathVariable("email") String email);
}
