package cl.hilton.pagos.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-huespedes")
public interface HuespedClient {

    @GetMapping("/huespedes")
    List<Map<String, Object>> listar();

    @GetMapping("/huespedes/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/huespedes/email/{email}")
    Map<String, Object> buscarPorEmail(@PathVariable("email") String email);
}
