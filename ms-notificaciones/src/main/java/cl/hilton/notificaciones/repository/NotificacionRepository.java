package cl.hilton.notificaciones.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.notificaciones.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByEventoOrigen(String eventoOrigen);

    List<Notificacion> findByPlantillaCodigo(String codigoPlantilla);

    List<Notificacion> findByHuespedEmail(String emailHuesped);

    List<Notificacion> findByCreadoEn(LocalDate creadoEn);
}
