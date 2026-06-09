package cl.hilton.housekeeping.event;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.event.HabitacionDeletedEvent;
import cl.hilton.common.event.HabitacionUpdatedEvent;
import cl.hilton.housekeeping.service.ProjHabitacionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitacionEventConsumer {

    private final ProjHabitacionService habitacionService;

    @Transactional
    @KafkaListener(
        topics = "habitaciones.habitacion.created",
        groupId = "housekeeping-group",
        properties = {"spring.json.value.default.type=cl.hilton.common.event.HabitacionCreatedEvent"}
    )
    public void onHabitacionCreated(HabitacionCreatedEvent event) {
        log.debug("Evento recibido [created] → numeroHabitacion: {}", event.getNumeroHabitacion());
        habitacionService.save(event.getNumeroHabitacion(), event.getCodigoTipo(), event.getPiso());
    }

    @Transactional
    @KafkaListener(
        topics = "habitaciones.habitacion.updated",
        groupId = "housekeeping-group",
        properties = {"spring.json.value.default.type=cl.hilton.common.event.HabitacionUpdatedEvent"}
    )
    public void onHabitacionUpdated(HabitacionUpdatedEvent event) {
        log.debug("Evento recibido [updated] → numeroHabitacion: {}", event.getNumeroHabitacion());
        habitacionService.save(event.getNumeroHabitacion(), event.getCodigoTipo(), event.getPiso());
    }

    @Transactional
    @KafkaListener(
        topics = "habitaciones.habitacion.deleted",
        groupId = "housekeeping-group",
        properties = {"spring.json.value.default.type=cl.hilton.common.event.HabitacionDeletedEvent"}
    )
    public void onHabitacionDeleted(HabitacionDeletedEvent event) {
        log.debug("Evento recibido [deleted] → numeroHabitacion: {}", event.getNumeroHabitacion());
        habitacionService.deleteByNumeroHabitacion(event.getNumeroHabitacion());
    }
}