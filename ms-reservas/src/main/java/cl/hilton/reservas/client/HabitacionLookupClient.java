package cl.hilton.reservas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.reservas.dto.ProjHabitacionResponse;

@FeignClient(name = "ms-habitaciones")
public interface HabitacionLookupClient {

    @GetMapping("/api/v1/habitaciones/habitaciones")
    List<ProjHabitacionResponse> listar();

    @GetMapping("/api/v1/habitaciones/habitaciones/{id}")
    ProjHabitacionResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/habitaciones/habitaciones/numero/{numeroHabitacion}")
    ProjHabitacionResponse buscarPorNumeroHabitacion(@PathVariable("numeroHabitacion") String numeroHabitacion);

    @GetMapping("/api/v1/habitaciones/habitaciones/activas/{activa}")
    List<ProjHabitacionResponse> buscarPorActiva(@PathVariable("activa") Boolean activa);
}