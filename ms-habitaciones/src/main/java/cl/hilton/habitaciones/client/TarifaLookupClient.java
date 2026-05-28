package cl.hilton.habitaciones.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-tarifas")
public interface TarifaLookupClient {

    @GetMapping("/tarifas")
    List<Map<String, Object>> listar();

    @GetMapping("/tarifas/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);
}
