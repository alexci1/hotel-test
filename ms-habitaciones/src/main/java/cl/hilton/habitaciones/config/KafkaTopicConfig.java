package cl.hilton.habitaciones.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topichabitacionCreated() {

        log.debug("Publicado topic Kafka → topic: {}", "habitaciones.habitacion.created");

        return TopicBuilder.name("habitaciones.habitacion.created")
                .partitions(1) // En desarrollo con 1 está bien
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topichabitacionUpdated() {

        log.debug("Publicado topic Kafka → topic: {}", "habitaciones.habitacion.updated");

        return TopicBuilder.name("habitaciones.habitacion.updated")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic topichabitacionDeleted() {

        log.debug("Publicado topic Kafka → topic: {}", "habitaciones.habitacion.deleted");
        
        return TopicBuilder.name("habitaciones.habitacion.deleted")
                .partitions(1)
                .build();
    }
}