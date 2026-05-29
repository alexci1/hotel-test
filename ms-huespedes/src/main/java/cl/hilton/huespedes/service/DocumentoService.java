package cl.hilton.huespedes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.mapper.DocumentoMapper;
import cl.hilton.huespedes.model.Documento;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.DocumentoRepository;
import cl.hilton.huespedes.repository.HuespedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
        return documentoMapper.toResponseList(documentoRepository.findByHuespedEmail(emailHuesped));
    }

    public List<DocumentoResponse> findByTipo(String tipo) {
        return documentoMapper.toResponseList(documentoRepository.findByTipo(tipo));
    }

    public List<DocumentoResponse> findByPaisEmisor(String paisEmisor) {
        return documentoMapper.toResponseList(documentoRepository.findByPaisEmisor(paisEmisor));
    }

    public List<DocumentoResponse> findByVencimiento(LocalDate vencimiento) {
        return documentoMapper.toResponseList(documentoRepository.findByVencimiento(vencimiento));
    }

    public DocumentoResponse findByTipoNumeroPais(String tipo, String numero, String paisEmisor) {
        Documento documento = documentoRepository.findByTipoAndNumeroAndPaisEmisor(tipo, numero, paisEmisor)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con los datos indicados"));

        return documentoMapper.toResponse(documento);
    }

    public DocumentoResponse create(DocumentoRequest request) {
        validarDocumentoUnico(request.getTipo(), request.getNumero(), request.getPaisEmisor());

        Huesped huesped = getHuespedByEmail(request.getEmailHuesped());

        Documento documento = documentoMapper.toEntity(request);
        documento.setHuesped(huesped);

        Documento documentoGuardado = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoGuardado);
    }

    public DocumentoResponse update(Long id, DocumentoRequest request) {
        Documento documento = getDocumentoById(id);

        if (!documento.getTipo().equalsIgnoreCase(request.getTipo())
                || !documento.getNumero().equalsIgnoreCase(request.getNumero())
                || !documento.getPaisEmisor().equalsIgnoreCase(request.getPaisEmisor())) {
            validarDocumentoUnico(request.getTipo(), request.getNumero(), request.getPaisEmisor());
        }

        Huesped huesped = getHuespedByEmail(request.getEmailHuesped());

        documentoMapper.updateEntity(request, documento);
        documento.setHuesped(huesped);

        Documento documentoActualizado = documentoRepository.save(documento);

        return documentoMapper.toResponse(documentoActualizado);
    }

    public void deleteById(Long id) {
        Documento documento = getDocumentoById(id);
        documentoRepository.delete(documento);
    }

    private Documento getDocumentoById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Documento no encontrado con id: " + id));
    }

    private Huesped getHuespedByEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped no encontrado con email: " + email));
    }

    private void validarDocumentoUnico(String tipo, String numero, String paisEmisor) {
        if (documentoRepository.existsByTipoAndNumeroAndPaisEmisor(tipo, numero, paisEmisor)) {
            throw new IllegalArgumentException("Ya existe un documento con tipo, numero y pais emisor indicados");
        }
    }
}
