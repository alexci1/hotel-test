package cl.hilton.housekeeping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.housekeeping.dto.ProjHabitacionResponse;

@FeignClient(name = "ms-habitaciones")
public interface HabitacionClient {

    @GetMapping("/api/v1/habitaciones/{id}")
    ProjHabitacionResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/habitaciones/numero/{numeroHabitacion}")
    ProjHabitacionResponse buscarPorNumeroHabitacion(@PathVariable("numeroHabitacion") String numeroHabitacion);
}