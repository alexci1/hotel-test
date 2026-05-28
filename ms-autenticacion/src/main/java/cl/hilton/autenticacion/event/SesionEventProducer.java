package cl.hilton.autenticacion.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SesionEventProducer {

    public static final String SESION_CREATED_TOPIC = "sesion.created";
    public static final String SESION_UPDATED_TOPIC = "sesion.updated";
    public static final String SESION_DELETED_TOPIC = "sesion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SesionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(SESION_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(SESION_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(SESION_DELETED_TOPIC, key, event);
    }
}
