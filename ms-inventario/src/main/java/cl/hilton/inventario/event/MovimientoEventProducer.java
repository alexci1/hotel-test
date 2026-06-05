package cl.hilton.inventario.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("null")
public class MovimientoEventProducer {

    public static final String MOVIMIENTO_CREATED_TOPIC = "movimiento.created";
    public static final String MOVIMIENTO_UPDATED_TOPIC = "movimiento.updated";
    public static final String MOVIMIENTO_DELETED_TOPIC = "movimiento.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MovimientoEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(MOVIMIENTO_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(MOVIMIENTO_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(MOVIMIENTO_DELETED_TOPIC, key, event);
    }
}
