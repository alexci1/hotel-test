package cl.hilton.notificaciones.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacionEventProducer {

    public static final String NOTIFICACION_CREATED_TOPIC = "notificacion.created";
    public static final String NOTIFICACION_UPDATED_TOPIC = "notificacion.updated";
    public static final String NOTIFICACION_DELETED_TOPIC = "notificacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public NotificacionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(NOTIFICACION_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(NOTIFICACION_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(NOTIFICACION_DELETED_TOPIC, key, event);
    }
}
