package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.pagos.dto.FacturaRequest;
import cl.hilton.pagos.dto.FacturaResponse;
import cl.hilton.pagos.mapper.FacturaMapper;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.model.ProjHuesped;
import cl.hilton.pagos.model.ProjReserva;
import cl.hilton.pagos.repository.FacturaRepository;
import cl.hilton.pagos.repository.ProjHuespedRepository;
import cl.hilton.pagos.repository.ProjReservaRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String numero = validarTexto(numeroFactura, "numeroFactura");

        Factura factura = facturaRepository.findByNumeroFactura(numero)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con numero: " + numero));

        return facturaMapper.toResponse(factura);
    }

    public FacturaResponse findByCodigoReserva(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        Factura factura = facturaRepository.findByReservaCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada para reserva: " + codigo));

        return facturaMapper.toResponse(factura);
    }

    public List<FacturaResponse> findByEmailHuesped(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");
        return facturaMapper.toResponseList(facturaRepository.findByHuespedEmail(email));
    }

    public List<FacturaResponse> findByEstado(String estado) {
        String estadoValido = validarTexto(estado, "estado");
        return facturaMapper.toResponseList(facturaRepository.findByEstado(estadoValido));
    }

    public List<FacturaResponse> findByEmitidaEn(LocalDate emitidaEn) {
        LocalDate fecha = validarFecha(emitidaEn, "emitidaEn");
        return facturaMapper.toResponseList(facturaRepository.findByEmitidaEn(fecha));
    }

    @Transactional
    public FacturaResponse create(FacturaRequest request) {
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        validarNumeroFacturaUnico(numeroFactura);

        if (facturaRepository.existsByReservaCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una factura para la reserva: " + codigoReserva);
        }

        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        Factura factura = facturaMapper.toEntity(request);
        factura.setReserva(reserva);
        factura.setHuesped(huesped);
        factura.setEmitidaEn(LocalDate.now());

        Factura facturaGuardada = facturaRepository.save(factura);

        return facturaMapper.toResponse(facturaGuardada);
    }

    @Transactional
    public FacturaResponse update(Long id, FacturaRequest request) {
        Long facturaId = validarId(id);
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");
        String codigoReserva = validarTexto(request.getCodigoReserva(), "codigoReserva");
        String emailHuesped = validarTexto(request.getEmailHuesped(), "emailHuesped");

        Factura factura = getFacturaById(facturaId);

        if (!factura.getNumeroFactura().equalsIgnoreCase(numeroFactura)) {
            validarNumeroFacturaUnico(numeroFactura);
        }

        if (!factura.getReserva().getCodigoReserva().equalsIgnoreCase(codigoReserva)
                && facturaRepository.existsByReservaCodigoReserva(codigoReserva)) {
            throw new IllegalArgumentException("Ya existe una factura para la reserva: " + codigoReserva);
        }

        ProjReserva reserva = getReservaByCodigo(codigoReserva);
        ProjHuesped huesped = getHuespedByEmail(emailHuesped);

        facturaMapper.updateEntity(request, factura);
        factura.setReserva(reserva);
        factura.setHuesped(huesped);

        Factura facturaActualizada = facturaRepository.save(factura);

        return facturaMapper.toResponse(facturaActualizada);
    }

    @Transactional
    public void deleteById(Long id) {
        Long facturaId = validarId(id);
        getFacturaById(facturaId);
        facturaRepository.deleteById(facturaId);
    }

    private Factura getFacturaById(Long id) {
        Long facturaId = validarId(id);

        return facturaRepository.findById(facturaId)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con id: " + facturaId));
    }

    private ProjReserva getReservaByCodigo(String codigoReserva) {
        String codigo = validarTexto(codigoReserva, "codigoReserva");

        return reservaRepository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reserva proyectada no encontrada: " + codigo));
    }

    private ProjHuesped getHuespedByEmail(String emailHuesped) {
        String email = validarTexto(emailHuesped, "emailHuesped");

        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado: " + email));
    }

    private void validarNumeroFacturaUnico(String numeroFactura) {
        if (facturaRepository.existsByNumeroFactura(numeroFactura)) {
            throw new IllegalArgumentException("Ya existe una factura con numero: " + numeroFactura);
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
