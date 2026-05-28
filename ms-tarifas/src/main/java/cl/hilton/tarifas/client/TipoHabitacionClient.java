package cl.hilton.tarifas.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-habitaciones")
public interface TipoHabitacionClient {

    @GetMapping("/tipos-habitacion")
    List<Map<String, Object>> listar();

    @GetMapping("/tipos-habitacion/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);
}
