package cl.hilton.notificaciones.event;

import cl.hilton.common.event.NotificacionCreatedEvent;
import cl.hilton.common.event.NotificacionDeletedEvent;
import cl.hilton.common.event.NotificacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificacionEventProducer {

    public static final String NOTIFICACION_CREATED_TOPIC = "notificacion.created";
    public static final String NOTIFICACION_UPDATED_TOPIC = "notificacion.updated";
    public static final String NOTIFICACION_DELETED_TOPIC = "notificacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(NotificacionCreatedEvent event) {
        kafkaTemplate.send(NOTIFICACION_CREATED_TOPIC, event.getCodigoPlantilla(), event);
    }

    public void publishUpdated(NotificacionUpdatedEvent event) {
        kafkaTemplate.send(NOTIFICACION_UPDATED_TOPIC, event.getCodigoPlantilla(), event);
    }

    public void publishDeleted(NotificacionDeletedEvent event) {
        kafkaTemplate.send(NOTIFICACION_DELETED_TOPIC, event.getCodigoPlantilla(), event);
    }
}
