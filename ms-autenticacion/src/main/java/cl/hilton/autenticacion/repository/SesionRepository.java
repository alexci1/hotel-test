package cl.hilton.autenticacion.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.autenticacion.model.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    Optional<Sesion> findByTokenHash(String tokenHash);

    boolean existsByTokenHash(String tokenHash);

    Optional<Sesion> findByUsuarioEmail(String usuarioEmail);

    boolean existsByUsuarioEmail(String usuarioEmail);

    List<Sesion> findByExpiraEn(LocalDate expiraEn);

    List<Sesion> findByInvalidada(Boolean invalidada);

    List<Sesion> findByInvalidadaFalse();
}
