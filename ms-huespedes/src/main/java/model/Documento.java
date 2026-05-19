package com.hotel.huespedes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "preferencia",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_preferencia_email_huesped", columnNames = "email_huesped")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false, unique = true)
    private Huesped huesped;

    @Column(name = "piso_preferido")
    private Short pisoPreferido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cama", length = 30)
    private TipoCama tipoCama;

    @Lob
    @Column(name = "alergias")
    private String alergias;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    public enum TipoCama {
        MATRIMONIAL, TWIN, KING, QUEEN
}