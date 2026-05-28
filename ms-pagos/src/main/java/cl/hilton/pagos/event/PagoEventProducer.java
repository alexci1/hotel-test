package cl.hilton.pagos.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagoEventProducer {

    public static final String PAGO_CREATED_TOPIC = "pago.created";
    public static final String PAGO_UPDATED_TOPIC = "pago.updated";
    public static final String PAGO_DELETED_TOPIC = "pago.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PagoEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(PAGO_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(PAGO_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(PAGO_DELETED_TOPIC, key, event);
    }
}
