package cl.hilton.checkin.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/reservas/{id}")
    Map<String, Object> buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/reservas/codigo/{codigoReserva}")
    Map<String, Object> buscarPorCodigoReserva(@PathVariable("codigoReserva") String codigoReserva);
}
