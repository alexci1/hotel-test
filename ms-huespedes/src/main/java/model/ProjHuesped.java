package com.hotel.checkin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "proj_huesped")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuesped {

    @Id
    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToMany(mappedBy = "huesped")
    private List<Checkin> checkins;

    }