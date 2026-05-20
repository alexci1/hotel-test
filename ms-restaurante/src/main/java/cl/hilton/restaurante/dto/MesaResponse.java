package cl.hilton.restaurante.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponse {

    private Integer id;
    private String numeroMesa;
    private Short capacidad;
    private String zona;
    private Boolean disponible;
}
