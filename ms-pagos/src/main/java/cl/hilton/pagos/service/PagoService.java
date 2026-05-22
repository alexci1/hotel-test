package cl.hilton.pagos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.pagos.dto.PagoRequest;
import cl.hilton.pagos.dto.PagoResponse;
import cl.hilton.pagos.mapper.PagoMapper;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.model.Pago;
import cl.hilton.pagos.repository.FacturaRepository;
import cl.hilton.pagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;
    private final PagoMapper pagoMapper;

    public List<PagoResponse> findAll() {
        return pagoMapper.toResponseList(pagoRepository.findAll());
    }

    public PagoResponse findById(Long id) {
        Pago pago = getPagoById(id);
        return pagoMapper.toResponse(pago);
    }

    public List<PagoResponse> findByNumeroFactura(String numeroFactura) {
        return pagoMapper.toResponseList(pagoRepository.findByFacturaNumeroFactura(numeroFactura));
    }

    public PagoResponse create(PagoRequest request) {
        Factura factura = facturaRepository.findByNumeroFactura(request.getNumeroFactura())
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + request.getNumeroFactura()));

        Pago pago = pagoMapper.toEntity(request);
        pago.setFactura(factura);
        pago.setPagadoEn(LocalDateTime.now().toString());

        Pago pagoGuardado = pagoRepository.save(pago);

        return pagoMapper.toResponse(pagoGuardado);
    }

    public PagoResponse update(Long id, PagoRequest request) {
        Pago pago = getPagoById(id);

        Factura factura = facturaRepository.findByNumeroFactura(request.getNumeroFactura())
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + request.getNumeroFactura()));

        pagoMapper.updateEntity(request, pago);
        pago.setFactura(factura);

        Pago pagoActualizado = pagoRepository.save(pago);

        return pagoMapper.toResponse(pagoActualizado);
    }

    public void deleteById(Long id) {
        Pago pago = getPagoById(id);
        pagoRepository.delete(pago);
    }

    private Pago getPagoById(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con id: " + id));
    }
}