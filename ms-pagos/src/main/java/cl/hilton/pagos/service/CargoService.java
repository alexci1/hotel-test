package cl.hilton.pagos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.pagos.dto.CargoRequest;
import cl.hilton.pagos.dto.CargoResponse;
import cl.hilton.pagos.mapper.CargoMapper;
import cl.hilton.pagos.model.Cargo;
import cl.hilton.pagos.model.Factura;
import cl.hilton.pagos.repository.CargoRepository;
import cl.hilton.pagos.repository.FacturaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CargoService {

    private final CargoRepository cargoRepository;
    private final FacturaRepository facturaRepository;
    private final CargoMapper cargoMapper;

    public List<CargoResponse> findAll() {
        return cargoMapper.toResponseList(cargoRepository.findAll());
    }

    public CargoResponse findById(Long id) {
        Cargo cargo = getCargoById(id);
        return cargoMapper.toResponse(cargo);
    }

    public List<CargoResponse> findByNumeroFactura(String numeroFactura) {
        String numero = validarTexto(numeroFactura, "numeroFactura");
        return cargoMapper.toResponseList(cargoRepository.findByFacturaNumeroFactura(numero));
    }

    public List<CargoResponse> findByOrigen(String origen) {
        String origenValido = validarTexto(origen, "origen");
        return cargoMapper.toResponseList(cargoRepository.findByOrigen(origenValido));
    }

    public List<CargoResponse> findByRegistradoEn(LocalDate registradoEn) {
        LocalDate fecha = validarFecha(registradoEn, "registradoEn");
        return cargoMapper.toResponseList(cargoRepository.findByRegistradoEn(fecha));
    }

    @Transactional
    public CargoResponse create(CargoRequest request) {
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");
        Factura factura = getFacturaByNumero(numeroFactura);

        Cargo cargo = cargoMapper.toEntity(request);
        cargo.setFactura(factura);
        cargo.setRegistradoEn(LocalDate.now());

        Cargo cargoGuardado = cargoRepository.save(cargo);

        return cargoMapper.toResponse(cargoGuardado);
    }

    @Transactional
    public CargoResponse update(Long id, CargoRequest request) {
        Long cargoId = validarId(id);
        String numeroFactura = validarTexto(request.getNumeroFactura(), "numeroFactura");

        Cargo cargo = getCargoById(cargoId);
        Factura factura = getFacturaByNumero(numeroFactura);

        cargoMapper.updateEntity(request, cargo);
        cargo.setFactura(factura);

        Cargo cargoActualizado = cargoRepository.save(cargo);

        return cargoMapper.toResponse(cargoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long cargoId = validarId(id);
        getCargoById(cargoId);
        cargoRepository.deleteById(cargoId);
    }

    private Cargo getCargoById(Long id) {
        Long cargoId = validarId(id);

        return cargoRepository.findById(cargoId)
                .orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con id: " + cargoId));
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
