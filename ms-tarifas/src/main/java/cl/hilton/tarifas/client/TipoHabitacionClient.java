package cl.hilton.tarifas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.tarifas.dto.ProjTipoHabitacionResponse;

@FeignClient(name = "ms-habitaciones")
public interface TipoHabitacionClient {

    @GetMapping("/api/v1/habitaciones/tipos-habitacion")
    List<ProjTipoHabitacionResponse> listar();

    @GetMapping("/api/v1/habitaciones/tipos-habitacion/{id}")
    ProjTipoHabitacionResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/habitaciones/tipos-habitacion/codigo/{codigo}")
    ProjTipoHabitacionResponse buscarPorCodigo(@PathVariable("codigo") String codigo);

    @GetMapping("/api/v1/habitaciones/tipos-habitacion/activos/{activo}")
    List<ProjTipoHabitacionResponse> buscarPorActivo(@PathVariable("activo") Boolean activo);
}
