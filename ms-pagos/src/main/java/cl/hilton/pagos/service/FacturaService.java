package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.pagos.dto.FacturaRequest;
import cl.hilton.pagos.dto.FacturaResponse;
import cl.hilton.pagos.mapper.FacturaMapper;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.model.ProjHuesped;
import cl.hilton.pagos.model.ProjReserva;
import cl.hilton.pagos.repository.FacturaRepository;
import cl.hilton.pagos.repository.ProjHuespedRepository;
import cl.hilton.pagos.repository.ProjReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProjReservaRepository reservaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final FacturaMapper facturaMapper;

    public List<FacturaResponse> findAll() {
        return facturaMapper.toResponseList(facturaRepository.findAll());
    }

    public FacturaResponse findById(Long id) {
        Factura factura = getFacturaById(id);
        return facturaMapper.toResponse(factura);
    }

    public FacturaResponse findByNumeroFactura(String numeroFactura) {
        Factura factura = facturaRepository.findByNumeroFactura(numeroFactura)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + numeroFactura));

        return facturaMapper.toResponse(factura);
    }

    public FacturaResponse findByCodigoReserva(String codigoReserva) {
        Factura factura = facturaRepository.findByReservaCodigoReserva(codigoReserva)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada para reserva: " + codigoReserva));

        return facturaMapper.toResponse(factura);
    }

    public List<FacturaResponse> findByEmailHuesped(String emailHuesped) {
        return facturaMapper.toResponseList(facturaRepository.findByHuespedEmail(emailHuesped));
    }

    public List<FacturaResponse> findByEstado(String estado) {
        return facturaMapper.toResponseList(facturaRepository.findByEstado(estado));
    }

    public List<FacturaResponse> findByEmitidaEn(LocalDate emitidaEn) {
        return facturaMapper.toResponseList(facturaRepository.findByEmitidaEn(emitidaEn));
    }

    public FacturaResponse create(FacturaRequest request) {
        validarNumeroFacturaUnico(request.getNumeroFactura());

        if (facturaRepository.existsByReservaCodigoReserva(request.getCodigoReserva())) {
            throw new IllegalArgumentException("Ya existe una factura para la reserva: " + request.getCodigoReserva());
        }

        ProjReserva reserva = reservaRepository.findByCodigoReserva(request.getCodigoReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva proyectada no encontrada: " + request.getCodigoReserva()));

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + request.getEmailHuesped()));

        Factura factura = facturaMapper.toEntity(request);
        factura.setReserva(reserva);
        factura.setHuesped(huesped);
        factura.setEmitidaEn(LocalDate.now());

        Factura facturaGuardada = facturaRepository.save(factura);

        return facturaMapper.toResponse(facturaGuardada);
    }

    public FacturaResponse update(Long id, FacturaRequest request) {
        Factura factura = getFacturaById(id);

        if (!factura.getNumeroFactura().equalsIgnoreCase(request.getNumeroFactura())) {
            validarNumeroFacturaUnico(request.getNumeroFactura());
        }

        if (!factura.getReserva().getCodigoReserva().equalsIgnoreCase(request.getCodigoReserva())
                && facturaRepository.existsByReservaCodigoReserva(request.getCodigoReserva())) {
            throw new IllegalArgumentException("Ya existe una factura para la reserva: " + request.getCodigoReserva());
        }

        ProjReserva reserva = reservaRepository.findByCodigoReserva(request.getCodigoReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva proyectada no encontrada: " + request.getCodigoReserva()));

        ProjHuesped huesped = huespedRepository.findByEmail(request.getEmailHuesped())
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + request.getEmailHuesped()));

        facturaMapper.updateEntity(request, factura);
        factura.setReserva(reserva);
        factura.setHuesped(huesped);

        Factura facturaActualizada = facturaRepository.save(factura);

        return facturaMapper.toResponse(facturaActualizada);
    }

    public void deleteById(Long id) {
        Factura factura = getFacturaById(id);
        facturaRepository.delete(factura);
    }

    private Factura getFacturaById(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + id));
    }

    private void validarNumeroFacturaUnico(String numeroFactura) {
        if (facturaRepository.existsByNumeroFactura(numeroFactura)) {
            throw new IllegalArgumentException("Ya existe una factura con numero: " + numeroFactura);
        }
    }
}
