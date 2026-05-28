package cl.hilton.pagos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.pagos.dto.CargoRequest;
import cl.hilton.pagos.dto.CargoResponse;
import cl.hilton.pagos.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pagos/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public List<CargoResponse> findAll() {
        return cargoService.findAll();
    }

    @GetMapping("/{id}")
    public CargoResponse findById(@PathVariable Long id) {
        return cargoService.findById(id);
    }

    @GetMapping("/factura/{numeroFactura}")
    public List<CargoResponse> findByNumeroFactura(@PathVariable String numeroFactura) {
        return cargoService.findByNumeroFactura(numeroFactura);
    }

    @GetMapping("/origen/{origen}")
    public List<CargoResponse> findByOrigen(@PathVariable String origen) {
        return cargoService.findByOrigen(origen);
    }

    @GetMapping("/fecha/{registradoEn}")
    public List<CargoResponse> findByRegistradoEn(@PathVariable LocalDate registradoEn) {
        return cargoService.findByRegistradoEn(registradoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoResponse create(@Valid @RequestBody CargoRequest request) {
        return cargoService.create(request);
    }

    @PutMapping("/{id}")
    public CargoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequest request) {
        return cargoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        cargoService.deleteById(id);
    }
}
