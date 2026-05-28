package cl.hilton.huespedes.event;

import cl.hilton.common.event.HuespedCreatedEvent;
import cl.hilton.common.event.HuespedDeletedEvent;
import cl.hilton.common.event.HuespedUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HuespedEventProducer {

    public static final String HUESPED_CREATED_TOPIC = "huesped.created";
    public static final String HUESPED_UPDATED_TOPIC = "huesped.updated";
    public static final String HUESPED_DELETED_TOPIC = "huesped.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(HuespedCreatedEvent event) {
        kafkaTemplate.send(HUESPED_CREATED_TOPIC, event.getEmail(), event);
    }

    public void publishUpdated(HuespedUpdatedEvent event) {
        kafkaTemplate.send(HUESPED_UPDATED_TOPIC, event.getEmail(), event);
    }

    public void publishDeleted(HuespedDeletedEvent event) {
        kafkaTemplate.send(HUESPED_DELETED_TOPIC, event.getEmail(), event);
    }
}
