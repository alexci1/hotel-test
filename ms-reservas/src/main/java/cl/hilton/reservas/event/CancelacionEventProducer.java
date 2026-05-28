package cl.hilton.reservas.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CancelacionEventProducer {

    public static final String CANCELACION_CREATED_TOPIC = "cancelacion.created";
    public static final String CANCELACION_UPDATED_TOPIC = "cancelacion.updated";
    public static final String CANCELACION_DELETED_TOPIC = "cancelacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CancelacionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(CANCELACION_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(CANCELACION_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(CANCELACION_DELETED_TOPIC, key, event);
    }
}
