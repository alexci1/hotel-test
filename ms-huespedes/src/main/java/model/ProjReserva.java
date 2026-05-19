package com.hotel.checkin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "proj_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjReserva {

    @Id
    @Column(name = "codigo_reserva", length = 20)
    private String codigoReserva;

    @Column(name = "email_huesped", nullable = false, length = 120)
    private String emailHuesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToOne(mappedBy = "reserva")
    private Checkin checkin;

    @OneToOne(mappedBy = "reserva")
    private Checkout checkout;

    @OneToMany(mappedBy = "reserva")
    private List<Llave> llaves;

    }