package cl.hilton.restaurante.service;


import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.model.ProjHuesped;
import cl.hilton.restaurante.repository.ProjHuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjHuespedService {

    private final ProjHuespedRepository huespedRepository;

    public List<ProjHuespedResponse> listar() {
        return huespedRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProjHuespedResponse buscarPorEmail(String email) {
        return toResponse(obtenerHuesped(email));
    }

    public List<ProjHuespedResponse> buscarPorHabitacion(String numeroHabitacion) {
        return huespedRepository.findByNumeroHabitacion(numeroHabitacion).stream().map(this::toResponse).toList();
    }

    public List<ProjHuespedResponse> buscarPorNombre(String nombre) {
        return huespedRepository.findByNombreCompletoContainingIgnoreCase(nombre).stream().map(this::toResponse).toList();
    }

    public ProjHuespedResponse crear(ProjHuespedRequest request) {
        if (huespedRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un huésped con ese email");
        }

        ProjHuesped huesped = ProjHuesped.builder()
                .email(request.getEmail())
                .nombreCompleto(request.getNombreCompleto())
                .numeroHabitacion(request.getNumeroHabitacion())
                .actualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now())
                .build();

        return toResponse(huespedRepository.save(huesped));
    }

    public ProjHuespedResponse actualizar(String email, ProjHuespedRequest request) {
        ProjHuesped huesped = obtenerHuesped(email);

        huesped.setNombreCompleto(request.getNombreCompleto());
        huesped.setNumeroHabitacion(request.getNumeroHabitacion());
        huesped.setActualizadoEn(request.getActualizadoEn() != null ? request.getActualizadoEn() : OffsetDateTime.now());

        return toResponse(huespedRepository.save(huesped));
    }

    public void eliminar(String email) {
        ProjHuesped huesped = obtenerHuesped(email);
        huespedRepository.delete(huesped);
    }

    private ProjHuesped obtenerHuesped(String email) {
        return huespedRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }

    private ProjHuespedResponse toResponse(ProjHuesped huesped) {
        return ProjHuespedResponse.builder()
                .email(huesped.getEmail())
                .nombreCompleto(huesped.getNombreCompleto())
                .numeroHabitacion(huesped.getNumeroHabitacion())
                .actualizadoEn(huesped.getActualizadoEn())
                .build();
    }
}
