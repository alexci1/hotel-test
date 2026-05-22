package cl.hilton.huespedes.service;

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.mapper.DocumentoMapper;
import cl.hilton.huespedes.model.Documento;
import cl.hilton.huespedes.model.Huesped;
import cl.hilton.huespedes.repository.DocumentoRepository;
import cl.hilton.huespedes.repository.HuespedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final HuespedRepository huespedRepository;
    private final DocumentoMapper documentoMapper;

    public DocumentoService(
            DocumentoRepository documentoRepository,
            HuespedRepository huespedRepository,
            DocumentoMapper documentoMapper
    ) {
        this.documentoRepository = documentoRepository;
        this.huespedRepository = huespedRepository;
        this.documentoMapper = documentoMapper;
    }

    public List<DocumentoResponse> listar() {
        return documentoRepository.findAll().stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public DocumentoResponse buscarPorId(Long id) {
        return documentoMapper.toResponse(obtenerDocumento(id));
    }

    public List<DocumentoResponse> buscarPorHuesped(String emailHuesped) {
        return documentoRepository.findByHuespedEmail(emailHuesped).stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public List<DocumentoResponse> buscarPorTipo(String tipo) {
        return documentoRepository.findByTipo(tipo).stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public List<DocumentoResponse> buscarPorPaisEmisor(String paisEmisor) {
        return documentoRepository.findByPaisEmisor(paisEmisor).stream()
                .map(documentoMapper::toResponse)
                .toList();
    }

    public DocumentoResponse crear(DocumentoRequest request) {
        if (documentoRepository.existsByTipoAndNumeroAndPaisEmisor(
                request.getTipo(),
                request.getNumero(),
                request.getPaisEmisor()
        )) {
            throw new RuntimeException("Ya existe un documento con esos datos");
        }

        Huesped huesped = obtenerHuespedPorEmail(request.getEmailHuesped());
        Documento documento = documentoMapper.toEntity(request, huesped);

        return documentoMapper.toResponse(documentoRepository.save(documento));
    }

    public DocumentoResponse actualizar(Long id, DocumentoRequest request) {
        Documento documento = obtenerDocumento(id);
        Huesped huesped = obtenerHuespedPorEmail(request.getEmailHuesped());

        documentoMapper.updateEntity(documento, request, huesped);

        return documentoMapper.toResponse(documentoRepository.save(documento));
    }

    public void eliminar(Long id) {
        Documento documento = obtenerDocumento(id);
        documentoRepository.delete(documento);
    }

    private Documento obtenerDocumento(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    private Huesped obtenerHuespedPorEmail(String email) {
        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }
}