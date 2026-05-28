package cl.hilton.reservas.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-habitaciones")
public interface HabitacionLookupClient {

    @GetMapping("/habitaciones/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);
}
