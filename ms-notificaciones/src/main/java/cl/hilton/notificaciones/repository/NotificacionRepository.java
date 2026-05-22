package cl.hilton.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.notificaciones.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    Optional<Notificacion> findByEventoOrigen(String eventoOrigen);

    boolean existsByEventoOrigen(String eventoOrigen);
}