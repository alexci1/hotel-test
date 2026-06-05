package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.pagos.dto.PagoRequest;
import cl.hilton.pagos.dto.PagoResponse;
import cl.hilton.pagos.mapper.PagoMapper;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.model.Pago;
import cl.hilton.pagos.repository.FacturaRepository;
import cl.hilton.pagos.repository.PagoRepository;
import cl.hilton.common.exception.EntityNotFoundException;
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
        String numero = validarTexto(numeroFactura, "numeroFactura");
        return pagoMapper.toResponseList(pagoRepository.findByFacturaNumeroFactura(numero));
    }

    public List<PagoResponse> findByMetodo(String metodo) {
        String metodoValido = validarTexto(metodo, "metodo");
        return pagoMapper.toResponseList(pagoRepository.findByMetodo(metodoValido));
    }

    public List<PagoResponse> findByPagadoEn(LocalDate pagadoEn) {
        LocalDate fecha = validarFecha(pagadoEn, "pagadoEn");
        return pagoMapper.toResponseList(pagoRepository.findByPagadoEn(fecha));
    }

    @Transactional
    public PagoResponse create(PagoRequest request) {
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");
        Factura factura = getFacturaByNumero(numeroFactura);

        Pago pago = pagoMapper.toEntity(request);
        pago.setFactura(factura);
        pago.setPagadoEn(LocalDate.now());

        Pago pagoGuardado = pagoRepository.save(pago);

        return pagoMapper.toResponse(pagoGuardado);
    }

    @Transactional
    public PagoResponse update(Long id, PagoRequest request) {
        Long pagoId = validarId(id);
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");

        Pago pago = getPagoById(pagoId);
        Factura factura = getFacturaByNumero(numeroFactura);

        pagoMapper.updateEntity(request, pago);
        pago.setFactura(factura);

        Pago pagoActualizado = pagoRepository.save(pago);

        return pagoMapper.toResponse(pagoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long pagoId = validarId(id);
        getPagoById(pagoId);
        pagoRepository.deleteById(pagoId);
    }

    private Pago getPagoById(Long id) {
        Long pagoId = validarId(id);

        return pagoRepository.findById(pagoId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con id: " + pagoId));
    }

    private Factura getFacturaByNumero(String numeroFactura) {
        String numero = validarTexto(numeroFactura, "numeroFactura");

        return facturaRepository.findByNumeroFactura(numero)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + numero));
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
