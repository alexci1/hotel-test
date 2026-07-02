package cl.hilton.habitaciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.exception.DuplicateResourceException;
import cl.hilton.common.exception.EntityNotFoundException;
import cl.hilton.habitaciones.client.TarifaLookupClient;
import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.event.HabitacionEventProducer;
import cl.hilton.habitaciones.mapper.HabitacionMapper;
import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.repository.HabitacionRepository;
import cl.hilton.habitaciones.repository.TipoHabitacionRepository;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private TipoHabitacionRepository tipoHabitacionRepository;

    @Mock
    private HabitacionMapper habitacionMapper;

    @Mock
    private HabitacionEventProducer habitacionEventProducer;

    @Mock
    private TarifaLookupClient tarifaLookupClient;

    @InjectMocks
    private HabitacionService habitacionService;

    @Test
    @DisplayName("Debe listar todas las habitaciones")
    void shouldFindAllHabitaciones() {
        // GIVEN
        Habitacion habitacion = buildHabitacion();
        HabitacionResponse response = buildResponse();

        when(habitacionRepository.findAll()).thenReturn(List.of(habitacion));
        when(habitacionMapper.toResponseList(List.of(habitacion))).thenReturn(List.of(response));

        // WHEN
        List<HabitacionResponse> resultado = habitacionService.findAll();

        // THEN
        assertEquals(1, resultado.size());
        assertEquals("101", resultado.get(0).getNumeroHabitacion());
        verify(habitacionRepository).findAll();
        verify(habitacionMapper).toResponseList(List.of(habitacion));
    }

    @Test
    @DisplayName("Debe buscar una habitacion por id cuando existe")
    void shouldFindHabitacionByIdWhenExists() {
        // GIVEN
        Habitacion habitacion = buildHabitacion();
        HabitacionResponse response = buildResponse();

        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(habitacionMapper.toResponse(habitacion)).thenReturn(response);

        // WHEN
        HabitacionResponse resultado = habitacionService.findById(1L);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("101", resultado.getNumeroHabitacion());
        verify(habitacionRepository).findById(1L);
        verify(habitacionMapper).toResponse(habitacion);
    }

    @Test
    @DisplayName("Debe lanzar error cuando la habitacion por id no existe")
    void shouldThrowEntityNotFoundWhenHabitacionByIdDoesNotExist() {
        // GIVEN
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN / THEN
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> habitacionService.findById(99L)
        );

        assertEquals("Habitacion no encontrada con id: 99", exception.getMessage());
        verify(habitacionRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe crear una habitacion cuando los datos son validos")
    void shouldCreateHabitacionWhenDataIsValid() {
        // GIVEN
        HabitacionRequest request = buildRequest();
        Habitacion habitacion = buildHabitacionWithoutId();
        TipoHabitacion tipoHabitacion = buildTipoHabitacion();
        Habitacion habitacionGuardada = buildHabitacion();
        HabitacionResponse response = buildResponse();

        when(habitacionRepository.existsByNumeroHabitacion("101")).thenReturn(false);
        when(tipoHabitacionRepository.findByCodigo("STD")).thenReturn(Optional.of(tipoHabitacion));
        when(tarifaLookupClient.existsTarifaActivaByTipoHabitacion("STD")).thenReturn(true);
        when(habitacionMapper.toEntity(request)).thenReturn(habitacion);
        when(habitacionRepository.save(habitacion)).thenReturn(habitacionGuardada);
        when(habitacionMapper.toResponse(habitacionGuardada)).thenReturn(response);

        // WHEN
        HabitacionResponse resultado = habitacionService.create(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("101", resultado.getNumeroHabitacion());
        assertEquals("STD", resultado.getCodigoTipo());
        assertEquals(true, habitacion.getActiva());
        assertEquals(tipoHabitacion, habitacion.getTipoHabitacion());

        verify(habitacionRepository).existsByNumeroHabitacion("101");
        verify(tipoHabitacionRepository).findByCodigo("STD");
        verify(tarifaLookupClient).existsTarifaActivaByTipoHabitacion("STD");
        verify(habitacionRepository).save(habitacion);
        verify(habitacionEventProducer).sendCreated(any(String.class), any(HabitacionCreatedEvent.class));
    }

    @Test
    @DisplayName("Debe lanzar error al crear una habitacion con numero duplicado")
    void shouldThrowDuplicateResourceWhenNumeroHabitacionAlreadyExists() {
        // GIVEN
        HabitacionRequest request = buildRequest();
        when(habitacionRepository.existsByNumeroHabitacion("101")).thenReturn(true);

        // WHEN / THEN
        assertThrows(DuplicateResourceException.class, () -> habitacionService.create(request));

        verify(habitacionRepository).existsByNumeroHabitacion("101");
        verifyNoInteractions(tipoHabitacionRepository);
        verifyNoInteractions(tarifaLookupClient);
        verifyNoInteractions(habitacionMapper);
        verifyNoInteractions(habitacionEventProducer);
    }

    @Test
    @DisplayName("Debe lanzar error al crear una habitacion sin tarifa activa")
    void shouldThrowEntityNotFoundWhenCreateWithoutActiveTarifa() {
        // GIVEN
        HabitacionRequest request = buildRequest();
        TipoHabitacion tipoHabitacion = buildTipoHabitacion();

        when(habitacionRepository.existsByNumeroHabitacion("101")).thenReturn(false);
        when(tipoHabitacionRepository.findByCodigo("STD")).thenReturn(Optional.of(tipoHabitacion));
        when(tarifaLookupClient.existsTarifaActivaByTipoHabitacion("STD")).thenReturn(false);

        // WHEN / THEN
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> habitacionService.create(request)
        );

        assertEquals("No existe una tarifa activa para el tipo de habitacion: STD", exception.getMessage());
        verify(habitacionRepository).existsByNumeroHabitacion("101");
        verify(tipoHabitacionRepository).findByCodigo("STD");
        verify(tarifaLookupClient).existsTarifaActivaByTipoHabitacion("STD");
        verifyNoInteractions(habitacionMapper);
        verifyNoInteractions(habitacionEventProducer);
    }

    @Test
    @DisplayName("Debe actualizar una habitacion manteniendo estado activo actual si request viene nulo")
    void shouldUpdateHabitacionKeepingCurrentActiveWhenRequestActivaIsNull() {
        // GIVEN
        HabitacionRequest request = buildRequest();
        request.setNumeroHabitacion("102");
        request.setActiva(null);

        Habitacion habitacionExistente = buildHabitacion();
        TipoHabitacion tipoHabitacion = buildTipoHabitacion();
        Habitacion habitacionActualizada = buildHabitacion();
        habitacionActualizada.setNumeroHabitacion("102");
        HabitacionResponse response = buildResponse();
        response.setNumeroHabitacion("102");

        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacionExistente));
        when(habitacionRepository.existsByNumeroHabitacion("102")).thenReturn(false);
        when(tipoHabitacionRepository.findByCodigo("STD")).thenReturn(Optional.of(tipoHabitacion));
        when(habitacionRepository.save(habitacionExistente)).thenReturn(habitacionActualizada);
        when(habitacionMapper.toResponse(habitacionActualizada)).thenReturn(response);

        // WHEN
        HabitacionResponse resultado = habitacionService.update(1L, request);

        // THEN
        assertEquals("102", resultado.getNumeroHabitacion());
        assertEquals(true, habitacionExistente.getActiva());
        assertEquals(tipoHabitacion, habitacionExistente.getTipoHabitacion());

        verify(habitacionMapper).updateEntity(request, habitacionExistente);
        verify(habitacionRepository).save(habitacionExistente);
    }

    @Test
    @DisplayName("Debe cambiar el estado activo de una habitacion")
    void shouldChangeHabitacionActiveStatus() {
        // GIVEN
        Habitacion habitacion = buildHabitacion();
        Habitacion habitacionActualizada = buildHabitacion();
        habitacionActualizada.setActiva(false);
        HabitacionResponse response = buildResponse();
        response.setActiva(false);

        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(habitacionRepository.save(habitacion)).thenReturn(habitacionActualizada);
        when(habitacionMapper.toResponse(habitacionActualizada)).thenReturn(response);

        // WHEN
        HabitacionResponse resultado = habitacionService.cambiarActiva(1L, false);

        // THEN
        assertEquals(false, resultado.getActiva());
        assertEquals(false, habitacion.getActiva());
        verify(habitacionRepository).findById(1L);
        verify(habitacionRepository).save(habitacion);
    }

    @Test
    @DisplayName("Debe eliminar una habitacion cuando existe")
    void shouldDeleteHabitacionWhenExists() {
        // GIVEN
        Habitacion habitacion = buildHabitacion();
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));

        // WHEN
        habitacionService.deleteById(1L);

        // THEN
        verify(habitacionRepository).findById(1L);
        verify(habitacionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar error cuando el id es nulo")
    void shouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        // GIVEN / WHEN / THEN
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> habitacionService.findById(null)
        );

        assertEquals("El id no puede ser nulo", exception.getMessage());
        verify(habitacionRepository, never()).findById(any());
    }

    private HabitacionRequest buildRequest() {
        HabitacionRequest request = new HabitacionRequest();
        request.setNumeroHabitacion("101");
        request.setPiso(1);
        request.setCodigoTipo("STD");
        request.setActiva(null);
        return request;
    }

    private Habitacion buildHabitacion() {
        return Habitacion.builder()
                .id(1L)
                .numeroHabitacion("101")
                .piso(1)
                .tipoHabitacion(buildTipoHabitacion())
                .activa(true)
                .build();
    }

    private Habitacion buildHabitacionWithoutId() {
        return Habitacion.builder()
                .numeroHabitacion("101")
                .piso(1)
                .activa(null)
                .build();
    }

    private TipoHabitacion buildTipoHabitacion() {
        return TipoHabitacion.builder()
                .id(1L)
                .codigo("STD")
                .descripcion("Habitacion estandar")
                .capacidadMax(2)
                .activo(true)
                .build();
    }

    private HabitacionResponse buildResponse() {
        HabitacionResponse response = new HabitacionResponse();
        response.setId(1L);
        response.setNumeroHabitacion("101");
        response.setPiso(1);
        response.setCodigoTipo("STD");
        response.setActiva(true);
        return response;
    }
}
