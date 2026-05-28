package cl.hilton.housekeeping.event;

import cl.hilton.common.event.AsignacionCreatedEvent;
import cl.hilton.common.event.AsignacionDeletedEvent;
import cl.hilton.common.event.AsignacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsignacionHousekeepingEventProducer {

    public static final String ASIGNACION_CREATED_TOPIC = "asignacion.created";
    public static final String ASIGNACION_UPDATED_TOPIC = "asignacion.updated";
    public static final String ASIGNACION_DELETED_TOPIC = "asignacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(AsignacionCreatedEvent event) {
        kafkaTemplate.send(ASIGNACION_CREATED_TOPIC, buildKey(event.getNumeroHabitacion(), event.getCodigoTarea()), event);
    }

    public void publishUpdated(AsignacionUpdatedEvent event) {
        kafkaTemplate.send(ASIGNACION_UPDATED_TOPIC, buildKey(event.getNumeroHabitacion(), event.getCodigoTarea()), event);
    }

    public void publishDeleted(AsignacionDeletedEvent event) {
        kafkaTemplate.send(ASIGNACION_DELETED_TOPIC, buildKey(event.getNumeroHabitacion(), event.getCodigoTarea()), event);
    }

    private String buildKey(String numeroHabitacion, String codigoTarea) {
        return numeroHabitacion + "-" + codigoTarea;
    }
}
