package cl.hilton.housekeeping.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AsignacionHousekeepingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AsignacionHousekeepingEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send("asignacion.created", key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send("asignacion.updated", key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send("asignacion.deleted", key, event);
    }
}
