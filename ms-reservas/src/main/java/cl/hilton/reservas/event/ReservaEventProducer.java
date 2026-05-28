package cl.hilton.reservas.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservaEventProducer {

    public static final String RESERVA_CREATED_TOPIC = "reserva.created";
    public static final String RESERVA_UPDATED_TOPIC = "reserva.updated";
    public static final String RESERVA_DELETED_TOPIC = "reserva.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ReservaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(RESERVA_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(RESERVA_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(RESERVA_DELETED_TOPIC, key, event);
    }
}
