package cl.hilton.autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.autenticacion.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    Optional<Rol> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}