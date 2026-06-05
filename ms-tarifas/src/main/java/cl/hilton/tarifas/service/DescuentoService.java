package cl.hilton.tarifas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.tarifas.dto.DescuentoRequest;
import cl.hilton.tarifas.dto.DescuentoResponse;
import cl.hilton.tarifas.mapper.DescuentoMapper;
import cl.hilton.tarifas.model.Descuento;
import cl.hilton.tarifas.repository.DescuentoRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;
    private final DescuentoMapper descuentoMapper;

    public List<DescuentoResponse> findAll() {
        return descuentoMapper.toResponseList(descuentoRepository.findAll());
    }

    public DescuentoResponse findById(Long id) {
        Descuento descuento = getDescuentoById(id);
        return descuentoMapper.toResponse(descuento);
    }

    public DescuentoResponse findByCodigoDescuento(String codigoDescuento) {
        Descuento descuento = descuentoRepository.findByCodigoDescuento(codigoDescuento)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con codigo: " + codigoDescuento));

        return descuentoMapper.toResponse(descuento);
    }

    public List<DescuentoResponse> findByAplicaA(String aplicaA) {
        return descuentoMapper.toResponseList(descuentoRepository.findByAplicaA(aplicaA));
    }

    public List<DescuentoResponse> findByActivo(Boolean activo) {
        return descuentoMapper.toResponseList(descuentoRepository.findByActivo(activo));
    }

    public List<DescuentoResponse> findByValidoDesde(LocalDate validoDesde) {
        return descuentoMapper.toResponseList(descuentoRepository.findByValidoDesde(validoDesde));
    }

    public List<DescuentoResponse> findByValidoHasta(LocalDate validoHasta) {
        return descuentoMapper.toResponseList(descuentoRepository.findByValidoHasta(validoHasta));
    }

    public DescuentoResponse create(DescuentoRequest request) {
        validarCodigoUnico(request.getCodigoDescuento());
        validarFechas(request.getValidoDesde(), request.getValidoHasta());

        Descuento descuento = descuentoMapper.toEntity(request);
        descuento.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Descuento descuentoGuardado = descuentoRepository.save(descuento);

        return descuentoMapper.toResponse(descuentoGuardado);
    }

    public DescuentoResponse update(Long id, DescuentoRequest request) {
        Descuento descuento = getDescuentoById(id);
        Boolean activoActual = descuento.getActivo();

        if (!descuento.getCodigoDescuento().equalsIgnoreCase(request.getCodigoDescuento())) {
            validarCodigoUnico(request.getCodigoDescuento());
        }

        validarFechas(request.getValidoDesde(), request.getValidoHasta());

        descuentoMapper.updateEntity(request, descuento);
        descuento.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Descuento descuentoActualizado = descuentoRepository.save(descuento);

        return descuentoMapper.toResponse(descuentoActualizado);
    }

    public void deleteById(Long id) {
        Descuento descuento = getDescuentoById(id);
        descuentoRepository.delete(descuento);
    }

    private Descuento getDescuentoById(Long id) {
        return descuentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Descuento no encontrado con id: " + id));
    }

    private void validarCodigoUnico(String codigoDescuento) {
        if (descuentoRepository.existsByCodigoDescuento(codigoDescuento)) {
            throw new IllegalArgumentException("Ya existe un descuento con codigo: " + codigoDescuento);
        }
    }

    private void validarFechas(LocalDate validoDesde, LocalDate validoHasta) {
        if (validoHasta.isBefore(validoDesde)) {
            throw new IllegalArgumentException("La fecha validoHasta debe ser igual o posterior a validoDesde");
        }
    }
}
