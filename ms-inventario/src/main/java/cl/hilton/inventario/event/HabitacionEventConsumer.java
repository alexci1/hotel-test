package cl.hilton.inventario.event;

import java.time.LocalDate;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.event.HabitacionDeletedEvent;
import cl.hilton.common.event.HabitacionUpdatedEvent;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HabitacionEventConsumer {

    private final ProjHabitacionRepository habitacionRepository;

    @Transactional
    @KafkaListener(topics = "habitaciones.habitacion.created", groupId = "inventario-group")
    public void onCreated(ConsumerRecord<String, HabitacionCreatedEvent> record) {
        HabitacionCreatedEvent event = record.value();
        log.debug("Evento recibido [created] → numeroHabitacion: {}", event.getNumeroHabitacion());

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(event.getNumeroHabitacion())
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(event.getNumeroHabitacion());
        habitacion.setTipo(event.getCodigoTipo());
        habitacion.setActualizadoEn(LocalDate.now());

        habitacionRepository.save(habitacion);
    }

    @Transactional
    @KafkaListener(topics = "habitaciones.habitacion.updated", groupId = "inventario-group")
    public void onUpdated(ConsumerRecord<String, HabitacionUpdatedEvent> record) {
        HabitacionUpdatedEvent event = record.value();
        log.debug("Evento recibido [updated] → numeroHabitacion: {}", event.getNumeroHabitacion());

        ProjHabitacion habitacion = habitacionRepository.findByNumeroHabitacion(event.getNumeroHabitacion())
                .orElseGet(ProjHabitacion::new);

        habitacion.setNumeroHabitacion(event.getNumeroHabitacion());
        habitacion.setTipo(event.getCodigoTipo());
        habitacion.setActualizadoEn(LocalDate.now());

        habitacionRepository.save(habitacion);
    }

    @Transactional
    @KafkaListener(topics = "habitaciones.habitacion.deleted", groupId = "inventario-group")
    public void onDeleted(ConsumerRecord<String, HabitacionDeletedEvent> record) {
        HabitacionDeletedEvent event = record.value();
        log.debug("Evento recibido [deleted] → numeroHabitacion: {}", event.getNumeroHabitacion());

        habitacionRepository.findByNumeroHabitacion(event.getNumeroHabitacion())
                .ifPresent(habitacionRepository::delete);
    }
}