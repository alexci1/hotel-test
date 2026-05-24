package cl.hilton.autenticacion.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")
    },
    indexes = {
        @Index(name = "idx_usuarios_rol_codigo",    columnList = "rol_codigo"),
        @Index(name = "idx_usuarios_activo", columnList = "activo")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_codigo", referencedColumnName = "codigo", nullable = false)
    private Rol rol;

    @Column(name = "hash_password", nullable = false, length = 255)
    private String hashPassword;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "creado_en",nullable = false)
    private LocalDate creadoEn;

    @Column(name = "ultimo_acceso",nullable = false)
    private LocalDate ultimoAcceso;

}
