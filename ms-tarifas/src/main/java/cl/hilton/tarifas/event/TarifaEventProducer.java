package cl.hilton.tarifas.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TarifaEventProducer {

    public static final String TARIFA_CREATED_TOPIC = "tarifa.created";
    public static final String TARIFA_UPDATED_TOPIC = "tarifa.updated";
    public static final String TARIFA_DELETED_TOPIC = "tarifa.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TarifaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(TARIFA_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(TARIFA_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(TARIFA_DELETED_TOPIC, key, event);
    }
}
