package cl.hilton.pagos.event;

import cl.hilton.common.event.PagoCreatedEvent;
import cl.hilton.common.event.PagoDeletedEvent;
import cl.hilton.common.event.PagoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagoEventProducer {

    public static final String PAGO_CREATED_TOPIC = "pago.created";
    public static final String PAGO_UPDATED_TOPIC = "pago.updated";
    public static final String PAGO_DELETED_TOPIC = "pago.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(PagoCreatedEvent event) {
        kafkaTemplate.send(PAGO_CREATED_TOPIC, event.getNumeroFactura(), event);
    }

    public void publishUpdated(PagoUpdatedEvent event) {
        kafkaTemplate.send(PAGO_UPDATED_TOPIC, event.getNumeroFactura(), event);
    }

    public void publishDeleted(PagoDeletedEvent event) {
        kafkaTemplate.send(PAGO_DELETED_TOPIC, event.getNumeroFactura(), event);
    }
}
