package cl.hilton.notificaciones.dto;

import lombok.Data;

@Data
public class PlantillaResponse {

    private Long id;
    private String codigo;
    private String canal;
    private String asunto;
    private String cuerpo;
    private Boolean activa;
}
