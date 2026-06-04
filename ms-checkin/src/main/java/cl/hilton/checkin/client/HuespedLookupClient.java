package cl.hilton.checkin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.hilton.checkin.dto.ProjHuespedResponse;

// Como estoy usando Eureka no necesito especificar el puerto en la Feign Client, por lo que:
// Puedo usar: @FeignClient(name = "ms-huespedes")
// en vez de:  @FeignClient(name = "ms-huespedes", url = "http://localhost:XXXX/api/v1/huespedes")
@FeignClient(name = "ms-huespedes")
public interface HuespedLookupClient {

    @GetMapping("/api/v1/huespedes")
    List<ProjHuespedResponse> listar();

    @GetMapping("/api/v1/huespedes/{id}")
    ProjHuespedResponse buscarPorId(@PathVariable Long id);

    @GetMapping("/api/v1/huespedes/email/{email}")
    ProjHuespedResponse buscarPorEmail(@PathVariable String email);
}


