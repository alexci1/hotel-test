package cl.hilton.habitaciones.event;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.event.HabitacionDeletedEvent;
import cl.hilton.common.event.HabitacionEvent;
import cl.hilton.common.event.HabitacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructorA
public class HabitacionEventProducer {

    private static final String TOPIC_BASE = "habitaciones.habitacion";
    private static final String NUMERO_NOT_NULL = "El número de habitación no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, HabitacionEvent> kafkaTemplate;

    private void send(String numeroHabitacion, HabitacionEvent event, String eventType) {
        String topic  = Objects.requireNonNull(String.format("%s.%s", TOPIC_BASE, eventType), TOPIC_NOT_NULL);
        String numero = Objects.requireNonNull(numeroHabitacion, NUMERO_NOT_NULL);

        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("");
        log.debug("Enviando evento Kafka → topic: {}, key: {}", topic, numero);
        log.debug("");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");

        kafkaTemplate.send(topic, numero, event);
    }

    public void sendCreated(String numeroHabitacion, HabitacionCreatedEvent event) {
        send(numeroHabitacion, event, "created");
    }

    public void sendUpdated(String numeroHabitacion, HabitacionUpdatedEvent event) {
        send(numeroHabitacion, event, "updated");
    }

    public void sendDeleted(String numeroHabitacion, HabitacionDeletedEvent event) {
        send(numeroHabitacion, event, "deleted");
    }
}