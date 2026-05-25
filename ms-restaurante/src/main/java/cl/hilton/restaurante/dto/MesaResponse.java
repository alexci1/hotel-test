package cl.hilton.restaurante.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponse {

    private Long id;
    private String numeroMesa;
    private Integer capacidad;
    private String zona;
    private Boolean disponible;
}