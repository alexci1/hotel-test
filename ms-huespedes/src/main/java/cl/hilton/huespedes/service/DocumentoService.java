package cl.hilton.huespedes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.mapper.DocumentoMapper;
import cl.hilton.huespedes.model.Documento;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.DocumentoRepository;
import cl.hilton.huespedes.repository.HuespedRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final HuespedRepository huespedRepository;
    private final DocumentoMapper documentoMapper;

    public List<DocumentoResponse> findAll() {
        return documentoMapper.toResponseList(documentoRepository.findAll());
    }

    public DocumentoResponse findById(Long id) {
        Documento documento = getDocumentoById(id);
        return documentoMapper.toResponse(documento);
    }

    public List<DocumentoResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return documentoMapper.toResponseList(documentoRepository.findByHuespedEmail(email));
    }

    public List<DocumentoResponse> findByTipo(String tipo) {
        String tipoValido = validarTexto(tipo, "tipo");
        return documentoMapper.toResponseList(documentoRepository.findByTipo(tipoValido));
    }

    public List<DocumentoResponse> findByPaisEmisor(String paisEmisor) {
        String pais = validarTexto(paisEmisor, "paisEmisor");
        return documentoMapper.toResponseList(documentoRepository.findByPaisEmisor(pais));
    }

    public List<DocumentoResponse> findByVencimiento(LocalDate vencimiento) {
        LocalDate fecha = validarFecha(vencimiento, "vencimiento");
        return documentoMapper.toResponseList(documentoRepository.findByVencimiento(fecha));
    }

    public DocumentoResponse findByTipoNumeroPais(String tipo, String numero, String paisEmisor) {
        String tipoValido = validarTexto(tipo, "tipo");
        String numeroValido = validarTexto(numero, "numero");
        String paisValido = validarTexto(paisEmisor, "paisEmisor");

        Documento documento = documentoRepository.findByTipoAndNumeroAndPaisEmisor(tipoValido, numeroValido, paisValido)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con los datos indicados"));

        return documentoMapper.toResponse(documento);
    }

    @Transactional
    public DocumentoResponse create(DocumentoRequest request) {
        String tipo = validarTexto(request.getTipo(), "tipo");
        String numero = validarTexto(request.getNumero(), "numero");
        String paisEmisor = validarTexto(request.getPaisEmisor(), "paisEmisor");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        validarDocumentoUnico(tipo, numero, paisEmisor);

        Huesped huesped = getHuespedByEmail(emailHuesped);

        Documento documento = documentoMapper.toEntity(request);
        documento.setHuesped(huesped);

        Documento documentoGuardado = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoGuardado);
    }

    @Transactional
    public DocumentoResponse update(Long id, DocumentoRequest request) {
        Long documentoId = validarId(id);
        String tipo = validarTexto(request.getTipo(), "tipo");
        String numero = validarTexto(request.getNumero(), "numero");
        String paisEmisor = validarTexto(request.getPaisEmisor(), "paisEmisor");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        Documento documento = getDocumentoById(documentoId);

        if (!documento.getTipo().equalsIgnoreCase(tipo)
                || !documento.getNumero().equalsIgnoreCase(numero)
                || !documento.getPaisEmisor().equalsIgnoreCase(paisEmisor)) {
            validarDocumentoUnico(tipo, numero, paisEmisor);
        }

        Huesped huesped = getHuespedByEmail(emailHuesped);

        documentoMapper.updateEntity(request, documento);
        documento.setHuesped(huesped);

        Documento documentoActualizado = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long documentoId = validarId(id);
        getDocumentoById(documentoId);
        documentoRepository.deleteById(documentoId);
    }

    private Documento getDocumentoById(Long id) {
        Long documentoId = validarId(id);

        return documentoRepository.findById(documentoId)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con id: " + documentoId));
    }

    private Huesped getHuespedByEmail(String email) {
        String emailValido = validarTexto(email, "email");

        return huespedRepository.findByEmail(emailValido)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + emailValido));
    }

    private void validarDocumentoUnico(String tipo, String numero, String paisEmisor) {
        if (documentoRepository.existsByTipoAndNumeroAndPaisEmisor(tipo, numero, paisEmisor)) {
            throw new IllegalArgumentException("Ya existe un documento con tipo, numero y pais emisor indicados");
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private LocalDate validarFecha(LocalDate valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
