package cl.hilton.habitaciones.service;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.exception.EntityNotFoundException;
import cl.hilton.habitaciones.client.TarifaLookupClient;
import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.event.HabitacionEventProducer;
import cl.hilton.habitaciones.mapper.HabitacionMapper;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.EstadoHabitacionRepository;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private TipoHabitacionRepository tipoHabitacionRepository;

    @Mock
    private EstadoHabitacionRepository estadoHabitacionRepository;

    @Mock
    private HabitacionMapper habitacionMapper;

    @Mock
    private HabitacionEventProducer habitacionEventProducer;

    @Mock
    private TarifaLookupClient tarifaLookupClient;

    @InjectMocks
    private HabitacionService habitacionService;

    @BeforeEach
    void setUp() {
        lenient().when(habitacionMapper.toResponse(any(Habitacion.class))).thenAnswer(invocation -> {
            Habitacion habitacion = invocation.getArgument(0);

            HabitacionResponse response = new HabitacionResponse();
            response.setId(habitacion.getId());
            response.setNumeroHabitacion(habitacion.getNumeroHabitacion());
            response.setPiso(habitacion.getPiso());
            response.setActiva(habitacion.getActiva());

            if (habitacion.getTipoHabitacion() != null) {
                response.setCodigoTipo(habitacion.getTipoHabitacion().getCodigo());
            }

            return response;
        });

        lenient().when(habitacionMapper.toResponseList(anyList())).thenAnswer(invocation -> {
            List<Habitacion> habitaciones = invocation.getArgument(0);
            return habitaciones.stream()
                    .map(habitacionMapper::toResponse)
                    .toList();
        });

        lenient().when(habitacionMapper.toEntity(any(HabitacionRequest.class))).thenAnswer(invocation -> {
            HabitacionRequest request = invocation.getArgument(0);

            Habitacion habitacion = new Habitacion();
            habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
            habitacion.setPiso(request.getPiso());
            habitacion.setActiva(request.getActiva());

            return habitacion;
        });

        lenient().doAnswer(invocation -> {
            HabitacionRequest request = invocation.getArgument(0);
            Habitacion habitacion = invocation.getArgument(1);

            habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
            habitacion.setPiso(request.getPiso());

            return null;
        }).when(habitacionMapper).updateEntity(any(HabitacionRequest.class), any(Habitacion.class));
    }

    @Test
    void findAll_DeberiaRetornarHabitaciones() {
        Habitacion habitacion = crearHabitacion(1L, "101", "SIMPLE");
        when(habitacionRepository.findAll()).thenReturn(List.of(habitacion));

        List<HabitacionResponse> resultado = habitacionService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("101", resultado.get(0).getNumeroHabitacion());
        verify(habitacionRepository).findAll();
    }

    @Test
    void findById_DeberiaLanzarEntityNotFoundException_CuandoNoExiste() {
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> habitacionService.findById(99L));

        verify(habitacionRepository).findById(99L);
        verify(habitacionMapper, never()).toResponse(any());
    }

    @Test
    void create_DeberiaGuardarHabitacionYEnviarEvento_CuandoDatosSonValidos() {
        HabitacionRequest request = crearRequest("202", 2, "SIMPLE", null);
        TipoHabitacion tipo = crearTipoHabitacion("SIMPLE");

        when(habitacionRepository.existsByNumeroHabitacion("202")).thenReturn(false);
        when(tipoHabitacionRepository.findByCodigo("SIMPLE")).thenReturn(Optional.of(tipo));
        when(tarifaLookupClient.existsTarifaActivaByTipoHabitacion("SIMPLE")).thenReturn(true);
        when(habitacionRepository.save(any(Habitacion.class))).thenAnswer(invocation -> {
            Habitacion habitacion = invocation.getArgument(0);
            habitacion.setId(10L);
            return habitacion;
        });

        HabitacionResponse resultado = habitacionService.create(request);

        assertEquals(10L, resultado.getId());
        assertEquals("202", resultado.getNumeroHabitacion());
        assertEquals(true, resultado.getActiva());
        verify(habitacionRepository).save(any(Habitacion.class));
        verify(habitacionEventProducer).sendCreated(eq("202"), any(HabitacionCreatedEvent.class));
    }

    @Test
    void update_DeberiaActualizarHabitacion_CuandoDatosSonValidos() {
        Habitacion habitacion = crearHabitacion(1L, "101", "SIMPLE");
        HabitacionRequest request = crearRequest("101", 3, "SIMPLE", true);

        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(tipoHabitacionRepository.findByCodigo("SIMPLE")).thenReturn(Optional.of(crearTipoHabitacion("SIMPLE")));
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);

        HabitacionResponse resultado = habitacionService.update(1L, request);

        assertEquals("101", resultado.getNumeroHabitacion());
        assertEquals(3, resultado.getPiso());
        verify(habitacionRepository).save(habitacion);
    }

    @Test
    void deleteById_DeberiaEliminarEstadoYHabitacion_CuandoExiste() {
        Habitacion habitacion = crearHabitacion(1L, "101", "SIMPLE");
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));

        habitacionService.deleteById(1L);

        verify(habitacionRepository).findById(1L);
        verify(estadoHabitacionRepository).deleteByHabitacionNumeroHabitacion("101");
        verify(estadoHabitacionRepository).flush();
        verify(habitacionRepository).delete(habitacion);
        verify(habitacionRepository).flush();
    }

    @Test
    void deleteById_DeberiaLanzarEntityNotFoundException_CuandoNoExiste() {
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> habitacionService.deleteById(99L));

        verify(habitacionRepository).findById(99L);
        verify(estadoHabitacionRepository, never()).deleteByHabitacionNumeroHabitacion(anyString());
        verify(habitacionRepository, never()).delete(any(Habitacion.class));
    }

    private HabitacionRequest crearRequest(String numero, Integer piso, String codigoTipo, Boolean activa) {
        HabitacionRequest request = new HabitacionRequest();
        request.setNumeroHabitacion(numero);
        request.setPiso(piso);
        request.setCodigoTipo(codigoTipo);
        request.setActiva(activa);
        return request;
    }

    private Habitacion crearHabitacion(Long id, String numero, String codigoTipo) {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(id);
        habitacion.setNumeroHabitacion(numero);
        habitacion.setPiso(1);
        habitacion.setTipoHabitacion(crearTipoHabitacion(codigoTipo));
        habitacion.setActiva(true);
        return habitacion;
    }

    private TipoHabitacion crearTipoHabitacion(String codigo) {
        TipoHabitacion tipoHabitacion = new TipoHabitacion();
        tipoHabitacion.setId(1L);
        tipoHabitacion.setCodigo(codigo);
        tipoHabitacion.setDescripcion("Tipo de prueba");
        tipoHabitacion.setCapacidadMax(2);
        tipoHabitacion.setActivo(true);
        return tipoHabitacion;
    }
}
