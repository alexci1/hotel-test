package cl.hilton.tarifas.event;

import cl.hilton.common.event.TarifaCreatedEvent;
import cl.hilton.common.event.TarifaDeletedEvent;
import cl.hilton.common.event.TarifaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TarifaEventProducer {

    public static final String TARIFA_CREATED_TOPIC = "tarifa.created";
    public static final String TARIFA_UPDATED_TOPIC = "tarifa.updated";
    public static final String TARIFA_DELETED_TOPIC = "tarifa.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(TarifaCreatedEvent event) {
        kafkaTemplate.send(TARIFA_CREATED_TOPIC, event.getCodigoTipoHabitacion(), event);
    }

    public void publishUpdated(TarifaUpdatedEvent event) {
        kafkaTemplate.send(TARIFA_UPDATED_TOPIC, event.getCodigoTipoHabitacion(), event);
    }

    public void publishDeleted(TarifaDeletedEvent event) {
        kafkaTemplate.send(TARIFA_DELETED_TOPIC, event.getCodigoTipoHabitacion(), event);
    }
}
