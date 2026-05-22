package cl.hilton.reservas.repository;

import cl.hilton.reservas.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByFecha(LocalDate fecha);

    List<Disponibilidad> findByDisponible(Boolean disponible);
}