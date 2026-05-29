package cl.hilton.reportes.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.reportes.dto.ReservaReporteResponse;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas")
    List<ReservaReporteResponse> listar();

    @GetMapping("/api/v1/reservas/{id}")
    ReservaReporteResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/reservas/codigo/{codigoReserva}")
    ReservaReporteResponse buscarPorCodigoReserva(@PathVariable("codigoReserva") String codigoReserva);

    @GetMapping("/api/v1/reservas/estado/{estado}")
    List<ReservaReporteResponse> buscarPorEstado(@PathVariable("estado") String estado);
}
