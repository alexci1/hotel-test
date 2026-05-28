package cl.hilton.notificaciones.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.notificaciones.dto.ProjHuespedResponse;

@FeignClient(name = "ms-huespedes")
public interface HuespedClient {

    @GetMapping("/api/v1/huespedes")
    List<ProjHuespedResponse> listar();

    @GetMapping("/api/v1/huespedes/{id}")
    ProjHuespedResponse buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/huespedes/email/{email}")
    ProjHuespedResponse buscarPorEmail(@PathVariable("email") String email);
}
