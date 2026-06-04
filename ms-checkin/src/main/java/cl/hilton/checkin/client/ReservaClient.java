
package cl.hilton.checkin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.checkin.dto.ProjReservaResponse;

// Como estoy usando Eureka no necesito especificar el puerto en la Feign Client, por lo que:
// Puedo usar: @FeignClient(name = "ms-reservas")
// en vez de:  @FeignClient(name = "ms-reservas", url = "http://localhost:XXXX/api/v1/reservas")
@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas")
    List<ProjReservaResponse> listar();

    @GetMapping("/api/v1/reservas/{id}")
    ProjReservaResponse buscarPorId(@PathVariable Long id);

    @GetMapping("/api/v1/reservas/codigo/{codigoReserva}")
    ProjReservaResponse buscarPorCodigoReserva(@PathVariable String codigoReserva);

    @GetMapping("/api/v1/reservas/estado/{estado}")
    List<ProjReservaResponse> buscarPorEstado(@PathVariable String estado);
}
