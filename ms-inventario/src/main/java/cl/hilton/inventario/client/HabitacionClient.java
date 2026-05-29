package cl.hilton.inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.inventario.dto.HabitacionInventarioResponse;

@FeignClient(name = "ms-habitaciones")
public interface HabitacionClient {

    @GetMapping("/api/v1/habitaciones/habitaciones/{id}")
    HabitacionInventarioResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/habitaciones/habitaciones/numero/{numeroHabitacion}")
    HabitacionInventarioResponse buscarPorNumeroHabitacion(@PathVariable("numeroHabitacion") String numeroHabitacion);
}
