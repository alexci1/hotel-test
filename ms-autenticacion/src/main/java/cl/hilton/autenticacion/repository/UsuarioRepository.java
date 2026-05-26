package cl.hilton.autenticacion.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.hilton.autenticacion.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByRolCodigo(String rolCodigo);

    List<Usuario> findByActivo(Boolean activo);

    List<Usuario> findByCreadoEn(LocalDate creadoEn);

    List<Usuario> findByUltimoAcceso(LocalDate ultimoAcceso);
}
