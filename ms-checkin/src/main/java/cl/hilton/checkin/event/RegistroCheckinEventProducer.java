package cl.hilton.checkin.event;

import cl.hilton.common.event.RegistroCheckinCreatedEvent;
import cl.hilton.common.event.RegistroCheckinRemovedEvent;
import cl.hilton.common.event.RegistroCheckinUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistroCheckinEventProducer {

    public static final String CHECKIN_CREATED_TOPIC = "checkin.created";
    public static final String CHECKIN_UPDATED_TOPIC = "checkin.updated";
    public static final String CHECKIN_REMOVED_TOPIC = "checkin.removed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(RegistroCheckinCreatedEvent event) {
        kafkaTemplate.send(CHECKIN_CREATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishUpdated(RegistroCheckinUpdatedEvent event) {
        kafkaTemplate.send(CHECKIN_UPDATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishRemoved(RegistroCheckinRemovedEvent event) {
        kafkaTemplate.send(CHECKIN_REMOVED_TOPIC, event.getCodigoReserva(), event);
    }
}
