package cl.hilton.huespedes.mapper;

import cl.hilton.huespedes.dto.DocumentoRequest;
import cl.hilton.huespedes.dto.DocumentoResponse;
import cl.hilton.huespedes.model.Documento;
import cl.hilton.huespedes.model.Huesped;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public Documento toEntity(DocumentoRequest request, Huesped huesped) {
        return Documento.builder()
                .huesped(huesped)
                .tipo(request.getTipo())
                .numero(request.getNumero())
                .paisEmisor(request.getPaisEmisor())
                .vencimiento(request.getVencimiento())
                .build();
    }

    public DocumentoResponse toResponse(Documento documento) {
        return DocumentoResponse.builder()
                .id(documento.getId())
                .emailHuesped(documento.getHuesped().getEmail())
                .nombreHuesped(documento.getHuesped().getNombreCompleto())
                .tipo(documento.getTipo())
                .numero(documento.getNumero())
                .paisEmisor(documento.getPaisEmisor())
                .vencimiento(documento.getVencimiento())
                .build();
    }

    public void updateEntity(Documento documento, DocumentoRequest request, Huesped huesped) {
        documento.setHuesped(huesped);
        documento.setTipo(request.getTipo());
        documento.setNumero(request.getNumero());
        documento.setPaisEmisor(request.getPaisEmisor());
        documento.setVencimiento(request.getVencimiento());
    }
}