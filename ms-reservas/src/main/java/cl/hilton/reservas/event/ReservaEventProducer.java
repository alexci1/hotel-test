package cl.hilton.reservas.event;

import cl.hilton.common.event.ReservaCreatedEvent;
import cl.hilton.common.event.ReservaDeletedEvent;
import cl.hilton.common.event.ReservaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaEventProducer {

    public static final String RESERVA_CREATED_TOPIC = "reserva.created";
    public static final String RESERVA_UPDATED_TOPIC = "reserva.updated";
    public static final String RESERVA_DELETED_TOPIC = "reserva.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(ReservaCreatedEvent event) {
        kafkaTemplate.send(RESERVA_CREATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishUpdated(ReservaUpdatedEvent event) {
        kafkaTemplate.send(RESERVA_UPDATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishDeleted(ReservaDeletedEvent event) {
        kafkaTemplate.send(RESERVA_DELETED_TOPIC, event.getCodigoReserva(), event);
    }
}
