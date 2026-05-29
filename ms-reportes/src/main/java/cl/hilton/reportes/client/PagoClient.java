package cl.hilton.reportes.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.reportes.dto.PagoReporteResponse;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @GetMapping("/api/v1/pagos/pagos")
    List<PagoReporteResponse> listar();

    @GetMapping("/api/v1/pagos/pagos/{id}")
    PagoReporteResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/pagos/pagos/factura/{numeroFactura}")
    List<PagoReporteResponse> buscarPorNumeroFactura(@PathVariable("numeroFactura") String numeroFactura);

    @GetMapping("/api/v1/pagos/pagos/metodo/{metodo}")
    List<PagoReporteResponse> buscarPorMetodo(@PathVariable("metodo") String metodo);

    @GetMapping("/api/v1/pagos/pagos/fecha/{pagadoEn}")
    List<PagoReporteResponse> buscarPorFecha(@PathVariable("pagadoEn") LocalDate pagadoEn);
}
