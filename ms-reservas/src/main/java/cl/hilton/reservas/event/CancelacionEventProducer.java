package cl.hilton.reservas.event;

import cl.hilton.common.event.CancelacionCreatedEvent;
import cl.hilton.common.event.CancelacionDeletedEvent;
import cl.hilton.common.event.CancelacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelacionEventProducer {

    public static final String CANCELACION_CREATED_TOPIC = "cancelacion.created";
    public static final String CANCELACION_UPDATED_TOPIC = "cancelacion.updated";
    public static final String CANCELACION_DELETED_TOPIC = "cancelacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(CancelacionCreatedEvent event) {
        kafkaTemplate.send(CANCELACION_CREATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishUpdated(CancelacionUpdatedEvent event) {
        kafkaTemplate.send(CANCELACION_UPDATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishDeleted(CancelacionDeletedEvent event) {
        kafkaTemplate.send(CANCELACION_DELETED_TOPIC, event.getCodigoReserva(), event);
    }
}
