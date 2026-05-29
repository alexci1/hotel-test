package cl.hilton.huespedes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.service.HuespedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/huespedes")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedService huespedService;

    @GetMapping
    public List<HuespedResponse> findAll() {
        return huespedService.findAll();
    }

    @GetMapping("/{id}")
    public HuespedResponse findById(@PathVariable Long id) {
        return huespedService.findById(id);
    }

    @GetMapping("/email/{email}")
    public HuespedResponse findByEmail(@PathVariable String email) {
        return huespedService.findByEmail(email);
    }

    @GetMapping("/nombre/{nombreCompleto}")
    public List<HuespedResponse> findByNombreCompleto(@PathVariable String nombreCompleto) {
        return huespedService.findByNombreCompleto(nombreCompleto);
    }

    @GetMapping("/activo/{activo}")
    public List<HuespedResponse> findByActivo(@PathVariable Boolean activo) {
        return huespedService.findByActivo(activo);
    }

    @GetMapping("/creado/{creadoEn}")
    public List<HuespedResponse> findByCreadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creadoEn) {
        return huespedService.findByCreadoEn(creadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HuespedResponse create(@Valid @RequestBody HuespedRequest request) {
        return huespedService.create(request);
    }

    @PutMapping("/{id}")
    public HuespedResponse update(@PathVariable Long id, @Valid @RequestBody HuespedRequest request) {
        return huespedService.update(id, request);
    }

    @PatchMapping("/{id}/activo")
    public HuespedResponse cambiarActivo(@PathVariable Long id, @RequestParam Boolean activo) {
        return huespedService.cambiarActivo(id, activo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        huespedService.deleteById(id);
    }
}
