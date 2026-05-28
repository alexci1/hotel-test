package cl.hilton.inventario.event;

import cl.hilton.common.event.MovimientoCreatedEvent;
import cl.hilton.common.event.MovimientoDeletedEvent;
import cl.hilton.common.event.MovimientoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovimientoEventProducer {

    public static final String MOVIMIENTO_CREATED_TOPIC = "movimiento.created";
    public static final String MOVIMIENTO_UPDATED_TOPIC = "movimiento.updated";
    public static final String MOVIMIENTO_DELETED_TOPIC = "movimiento.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(MovimientoCreatedEvent event) {
        kafkaTemplate.send(MOVIMIENTO_CREATED_TOPIC, event.getCodigoProducto(), event);
    }

    public void publishUpdated(MovimientoUpdatedEvent event) {
        kafkaTemplate.send(MOVIMIENTO_UPDATED_TOPIC, event.getCodigoProducto(), event);
    }

    public void publishDeleted(MovimientoDeletedEvent event) {
        kafkaTemplate.send(MOVIMIENTO_DELETED_TOPIC, event.getCodigoProducto(), event);
    }
}
