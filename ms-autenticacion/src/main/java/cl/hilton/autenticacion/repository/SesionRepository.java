package cl.hilton.autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.autenticacion.model.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion,Long>{
    
    Optional<Sesion> findByTokenHash(String tokenHash);

    boolean existsByTokenHash(String tokenHash);
}
