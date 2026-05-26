package cl.hilton.notificaciones.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.notificaciones.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Optional<Envio> findByNotificacionId(Long notificacionId);

    boolean existsByNotificacionId(Long notificacionId);

    List<Envio> findByEstado(String estado);

    List<Envio> findByEnviadoEn(LocalDate enviadoEn);
}
