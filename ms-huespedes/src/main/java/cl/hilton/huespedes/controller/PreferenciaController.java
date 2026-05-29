package cl.hilton.huespedes.controller;

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

import cl.hilton.huespedes.dto.PreferenciaRequest;
import cl.hilton.huespedes.dto.PreferenciaResponse;
import cl.hilton.huespedes.service.PreferenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/huespedes/preferencias")
@RequiredArgsConstructor
public class PreferenciaController {

    private final PreferenciaService preferenciaService;

    @GetMapping
    public List<PreferenciaResponse> findAll() {
        return preferenciaService.findAll();
    }

    @GetMapping("/{id}")
    public PreferenciaResponse findById(@PathVariable Long id) {
        return preferenciaService.findById(id);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public PreferenciaResponse findByEmailHuesped(@PathVariable String emailHuesped) {
        return preferenciaService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/tipo-cama/{tipoCama}")
    public List<PreferenciaResponse> findByTipoCama(@PathVariable String tipoCama) {
        return preferenciaService.findByTipoCama(tipoCama);
    }

    @GetMapping("/piso/{pisoPreferido}")
    public List<PreferenciaResponse> findByPisoPreferido(@PathVariable Integer pisoPreferido) {
        return preferenciaService.findByPisoPreferido(pisoPreferido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PreferenciaResponse create(@Valid @RequestBody PreferenciaRequest request) {
        return preferenciaService.create(request);
    }

    @PutMapping("/{id}")
    public PreferenciaResponse update(@PathVariable Long id, @Valid @RequestBody PreferenciaRequest request) {
        return preferenciaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        preferenciaService.deleteById(id);
    }
}
