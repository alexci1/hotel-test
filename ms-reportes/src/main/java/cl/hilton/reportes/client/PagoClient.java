package cl.hilton.reportes.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @GetMapping("/pagos")
    List<Map<String, Object>> listar();

    @GetMapping("/pagos/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);
}
