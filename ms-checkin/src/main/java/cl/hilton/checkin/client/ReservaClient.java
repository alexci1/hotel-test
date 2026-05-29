package cl.hilton.checkin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.checkin.dto.ProjReservaResponse;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas")
    List<ProjReservaResponse> listar();

    @GetMapping("/api/v1/reservas/{id}")
    ProjReservaResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/reservas/codigo/{codigoReserva}")
    ProjReservaResponse buscarPorCodigoReserva(@PathVariable("codigoReserva") String codigoReserva);

    @GetMapping("/api/v1/reservas/estado/{estado}")
    List<ProjReservaResponse> buscarPorEstado(@PathVariable("estado") String estado);
}
