package cl.hilton.housekeeping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.housekeeping.dto.ProjHabitacionResponse;

// Como estoy usando Eureka no necesito especificar el puerto en la Feign Client, por lo que:
// Puedo usar: @FeignClient(name = "ms-habitaciones")
// en vez de:  @FeignClient(name = "ms-habitaciones", url = "http://localhost:XXXX/api/v1/habitaciones")
@FeignClient(name = "ms-habitaciones")
public interface HabitacionClient {

    @GetMapping("/api/v1/habitaciones/habitaciones/{id}")
    ProjHabitacionResponse buscarPorId(@PathVariable Long id);

    @GetMapping("/api/v1/habitaciones/habitaciones/numero/{numeroHabitacion}")
    ProjHabitacionResponse buscarPorNumeroHabitacion(@PathVariable String numeroHabitacion);
}

