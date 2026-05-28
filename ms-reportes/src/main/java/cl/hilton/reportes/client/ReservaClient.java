package cl.hilton.reportes.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/reservas")
    List<Map<String, Object>> listar();

    @GetMapping("/reservas/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);
}
