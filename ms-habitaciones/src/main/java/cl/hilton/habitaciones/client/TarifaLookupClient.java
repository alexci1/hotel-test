package cl.hilton.habitaciones.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.habitaciones.dto.TarifaHabitacionResponse;

@FeignClient(name = "ms-tarifas")
public interface TarifaLookupClient {

    @GetMapping("/api/v1/tarifas/tarifas")
    List<TarifaHabitacionResponse> listar();

    @GetMapping("/api/v1/tarifas/tarifas/{id}")
    TarifaHabitacionResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/tarifas/tarifas/tipo/{tipoHabitacion}/activa/{activa}")
    List<TarifaHabitacionResponse> buscarPorTipoHabitacionYActiva(
            @PathVariable("tipoHabitacion") String tipoHabitacion,
            @PathVariable("activa") Boolean activa);
}
