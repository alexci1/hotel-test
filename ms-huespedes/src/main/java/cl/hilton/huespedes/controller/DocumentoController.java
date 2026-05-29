package cl.hilton.huespedes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/huespedes/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @GetMapping
    public List<DocumentoResponse> findAll() {
        return documentoService.findAll();
    }

    @GetMapping("/{id}")
    public DocumentoResponse findById(@PathVariable Long id) {
        return documentoService.findById(id);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<DocumentoResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return documentoService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/tipo/{tipo}")
    public List<DocumentoResponse> findByTipo(@PathVariable String tipo) {
        return documentoService.findByTipo(tipo);
    }

    @GetMapping("/pais/{paisEmisor}")
    public List<DocumentoResponse> findByPaisEmisor(@PathVariable String paisEmisor) {
        return documentoService.findByPaisEmisor(paisEmisor);
    }

    @GetMapping("/vencimiento/{vencimiento}")
    public List<DocumentoResponse> findByVencimiento(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vencimiento) {
        return documentoService.findByVencimiento(vencimiento);
    }

    @GetMapping("/tipo/{tipo}/numero/{numero}/pais/{paisEmisor}")
    public DocumentoResponse findByTipoNumeroPais(
            @PathVariable String tipo,
            @PathVariable String numero,
            @PathVariable String paisEmisor) {
        return documentoService.findByTipoNumeroPais(tipo, numero, paisEmisor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentoResponse create(@Valid @RequestBody DocumentoRequest request) {
        return documentoService.create(request);
    }

    @PutMapping("/{id}")
    public DocumentoResponse update(@PathVariable Long id, @Valid @RequestBody DocumentoRequest request) {
        return documentoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        documentoService.deleteById(id);
    }
}
