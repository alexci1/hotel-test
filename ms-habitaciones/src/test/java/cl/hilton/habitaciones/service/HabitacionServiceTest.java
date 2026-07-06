package cl.hilton.habitaciones.service;

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
import net.datafaker.Faker;
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

/**
 * Clase de pruebas unitarias para HabitacionService.
 *
 * Mockito permite aislar HabitacionService de sus dependencias reales:
 * repositorios, mapper, cliente externo y productor de eventos.
 */
@ExtendWith(MockitoExtension.class)
class HabitacionServiceTest {

    // @Mock crea una dependencia simulada, evitando usar la base de datos real.
    @Mock
    private HabitacionRepository habitacionRepository;

    // Mock del repositorio de tipos de habitación.
    @Mock
    private TipoHabitacionRepository tipoHabitacionRepository;

    // Mock del mapper para controlar la conversión entre entidad y DTO.
    @Mock
    private HabitacionMapper habitacionMapper;

    // Mock del productor de eventos para evitar publicar eventos reales.
    @Mock
    private HabitacionEventProducer habitacionEventProducer;

    // Mock del cliente externo para evitar llamadas reales a otro servicio.
    @Mock
    private TarifaLookupClient tarifaLookupClient;

    // @InjectMocks crea HabitacionService e inyecta automáticamente los mocks anteriores.
    @InjectMocks
    private HabitacionService habitacionService;

    // DataFaker genera datos aleatorios para los objetos de prueba.
    private final Faker faker = new Faker();

    /**
     * Configuración común antes de cada prueba.
     *
     * Aquí se define cómo deben comportarse algunos mocks usados por varias pruebas.
     */
    @BeforeEach
    void setUp() {

        /*
         * lenient():
         * Evita que Mockito falle si alguna prueba no usa esta configuración.
         *
         * when(...):
         * Define qué método del mock se está simulando.
         *
         * any(Habitacion.class):
         * Acepta cualquier instancia de Habitacion como argumento.
         *
         * thenAnswer(...):
         * Permite crear una respuesta dinámica usando el argumento recibido.
         */
        lenient().when(habitacionMapper.toResponse(any(Habitacion.class))).thenAnswer(invocation -> {
            Habitacion habitacion = invocation.getArgument(0);

            if (habitacion == null) return null;

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

        /*
         * Simula la conversión de una lista de Habitacion a una lista de HabitacionResponse.
         */
        lenient().when(habitacionMapper.toResponseList(anyList())).thenAnswer(invocation -> {
            List<Habitacion> habitaciones = invocation.getArgument(0);

            if (habitaciones == null) return null;

            return habitaciones.stream()
                    .map(habitacionMapper::toResponse)
                    .toList();
        });

        /*
         * Simula la conversión desde HabitacionRequest hacia Habitacion.
         */
        lenient().when(habitacionMapper.toEntity(any(HabitacionRequest.class))).thenAnswer(invocation -> {
            HabitacionRequest request = invocation.getArgument(0);

            if (request == null) return null;

            Habitacion habitacion = new Habitacion();

            habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
            habitacion.setPiso(request.getPiso());
            habitacion.setActiva(request.getActiva());

            return habitacion;
        });

        /*
         * doAnswer(...):
         * Se usa aquí porque updateEntity(...) es un método void.
         * Permite simular que el mapper copia los datos del request a la habitación.
         */
        lenient().doAnswer(invocation -> {
            HabitacionRequest request = invocation.getArgument(0);
            Habitacion habitacion = invocation.getArgument(1);

            if (request != null && habitacion != null) {
                habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
                habitacion.setPiso(request.getPiso());
            }

            return null;

        }).when(habitacionMapper).updateEntity(any(HabitacionRequest.class), any(Habitacion.class));
    }

    /**
     * Genera un número ficticio de habitación para las pruebas.
     */
    private String generateFakeNumeroHabitacion() {
        return String.valueOf(faker.number().numberBetween(100, 999));
    }

    /**
     * Genera un código ficticio de tipo de habitación para las pruebas.
     */
    private String generateFakeCodigoTipo() {
        return faker.options().option("STD", "SIMPLE", "DOBLE", "SUITE", "FAMILIAR");
    }

    /**
     * Crea una entidad TipoHabitacion con datos aleatorios.
     */
    private TipoHabitacion crearTipoHabitacionSimulado(String codigo) {
        TipoHabitacion tipoHabitacion = new TipoHabitacion();

        tipoHabitacion.setId(faker.number().numberBetween(1L, 100L));
        tipoHabitacion.setCodigo(codigo);
        tipoHabitacion.setDescripcion(faker.lorem().sentence(3));
        tipoHabitacion.setCapacidadMax(faker.number().numberBetween(1, 6));
        tipoHabitacion.setActivo(true);

        return tipoHabitacion;
    }

    /**
     * Crea una entidad Habitacion con datos aleatorios.
     */
    private Habitacion crearHabitacionSimulada(Long id) {
        String codigoTipo = generateFakeCodigoTipo();

        Habitacion habitacion = new Habitacion();

        habitacion.setId(id);
        habitacion.setNumeroHabitacion(generateFakeNumeroHabitacion());
        habitacion.setPiso(faker.number().numberBetween(1, 20));
        habitacion.setTipoHabitacion(crearTipoHabitacionSimulado(codigoTipo));
        habitacion.setActiva(true);

        return habitacion;
    }

    /**
     * Crea una entidad Habitacion sin ID para simular una habitación antes de guardarse.
     */
    private Habitacion crearHabitacionSinIdSimulada(HabitacionRequest request) {
        Habitacion habitacion = new Habitacion();

        habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
        habitacion.setPiso(request.getPiso());
        habitacion.setActiva(request.getActiva());

        return habitacion;
    }

    /**
     * Crea un HabitacionRequest con datos aleatorios.
     */
    private HabitacionRequest crearHabitacionRequestSimulado() {
        HabitacionRequest request = new HabitacionRequest();

        request.setNumeroHabitacion(generateFakeNumeroHabitacion());
        request.setPiso(faker.number().numberBetween(1, 20));
        request.setCodigoTipo(generateFakeCodigoTipo());
        request.setActiva(null);

        return request;
    }

    /**
     * Prueba findAll() cuando existen habitaciones registradas.
     */
    @Test
    void findAll_DeberiaRetornarListaDeHabitaciones_CuandoExistenRegistros() {
        Habitacion habitacion1 = crearHabitacionSimulada(1L);
        Habitacion habitacion2 = crearHabitacionSimulada(2L);
        Habitacion habitacion3 = crearHabitacionSimulada(3L);

        // when(...).thenReturn(...): simula lo que devuelve el repositorio.
        when(habitacionRepository.findAll()).thenReturn(List.of(habitacion1, habitacion2, habitacion3));

        List<HabitacionResponse> resultado = habitacionService.findAll();

        // assertNotNull: verifica que el resultado no sea null.
        assertNotNull(resultado, "La lista retornada no debe ser nula");

        // assertEquals: compara el valor esperado con el valor obtenido.
        assertEquals(3, resultado.size(), "La lista debe contener exactamente 3 elementos");

        HabitacionResponse primerRegistro = resultado.get(0);

        assertEquals(habitacion1.getId(), primerRegistro.getId(), "El ID debe coincidir");
        assertEquals(habitacion1.getNumeroHabitacion(), primerRegistro.getNumeroHabitacion(), "El número debe coincidir");
        assertEquals(habitacion1.getPiso(), primerRegistro.getPiso(), "El piso debe coincidir");
        assertEquals(habitacion1.getTipoHabitacion().getCodigo(), primerRegistro.getCodigoTipo(), "El código del tipo debe coincidir");
        assertEquals(habitacion1.getActiva(), primerRegistro.getActiva(), "El estado activo debe coincidir");

        // verify: comprueba que el método del mock fue llamado.
        verify(habitacionRepository).findAll();
    }

    /**
     * Prueba findById() cuando el ID existe.
     */
    @Test
    void findById_DeberiaRetornarHabitacion_CuandoIdExiste() {
        Long id = 10L;
        Habitacion habitacion = crearHabitacionSimulada(id);

        // Simula que el repositorio encuentra la habitación.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacion));

        HabitacionResponse resultado = habitacionService.findById(id);

        // Verifica que el servicio retorne un objeto válido.
        assertNotNull(resultado);

        // Compara los datos esperados con los datos retornados.
        assertEquals(habitacion.getId(), resultado.getId());
        assertEquals(habitacion.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals(habitacion.getPiso(), resultado.getPiso());

        // Verifica que se haya buscado por ID.
        verify(habitacionRepository).findById(id);
    }

    /**
     * Prueba findById() cuando el ID no existe.
     */
    @Test
    void findById_DeberiaLanzarEntityNotFoundException_CuandoIdNoExiste() {
        Long id = 999L;

        // Simula que el repositorio no encuentra la habitación.
        when(habitacionRepository.findById(id)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción esperada.
        assertThrows(EntityNotFoundException.class, () -> habitacionService.findById(id));

        // Verifica que se haya consultado el repositorio.
        verify(habitacionRepository).findById(id);
    }

    /**
     * Prueba findByNumeroHabitacion() cuando el número existe.
     */
    @Test
    void findByNumeroHabitacion_DeberiaRetornarHabitacion_CuandoNumeroExiste() {
        String numero = generateFakeNumeroHabitacion();

        Habitacion habitacion = crearHabitacionSimulada(1L);
        habitacion.setNumeroHabitacion(numero);

        // Simula que el repositorio encuentra la habitación por número.
        when(habitacionRepository.findByNumeroHabitacion(numero)).thenReturn(Optional.of(habitacion));

        HabitacionResponse resultado = habitacionService.findByNumeroHabitacion(numero);

        // Verifica que el resultado no sea null.
        assertNotNull(resultado);

        // Verifica que los datos retornados sean los esperados.
        assertEquals(numero, resultado.getNumeroHabitacion());
        assertEquals(habitacion.getPiso(), resultado.getPiso());

        // Verifica que se haya llamado al método correcto del repositorio.
        verify(habitacionRepository).findByNumeroHabitacion(numero);
    }

    /**
     * Prueba findByNumeroHabitacion() cuando el número no existe.
     */
    @Test
    void findByNumeroHabitacion_DeberiaLanzarEntityNotFoundException_CuandoNumeroNoExiste() {
        String numero = generateFakeNumeroHabitacion();

        // Simula que no existe una habitación con ese número.
        when(habitacionRepository.findByNumeroHabitacion(numero)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción esperada.
        assertThrows(EntityNotFoundException.class, () -> habitacionService.findByNumeroHabitacion(numero));

        // Verifica que se haya buscado por número.
        verify(habitacionRepository).findByNumeroHabitacion(numero);
    }

    /**
     * Prueba findByPiso() cuando existen habitaciones en ese piso.
     */
    @Test
    void findByPiso_DeberiaRetornarListaDeHabitaciones_CuandoExistenRegistros() {
        Integer piso = faker.number().numberBetween(1, 20);

        Habitacion habitacion = crearHabitacionSimulada(1L);
        habitacion.setPiso(piso);

        // Simula que existen habitaciones en el piso indicado.
        when(habitacionRepository.findByPiso(piso)).thenReturn(List.of(habitacion));

        List<HabitacionResponse> resultado = habitacionService.findByPiso(piso);

        // Verifica que la respuesta sea válida.
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(piso, resultado.get(0).getPiso());

        // Verifica que se haya consultado por piso.
        verify(habitacionRepository).findByPiso(piso);
    }

    /**
     * Prueba findByActiva() cuando existen habitaciones con ese estado.
     */
    @Test
    void findByActiva_DeberiaRetornarListaDeHabitaciones_CuandoEstadoExiste() {
        Boolean activa = true;

        Habitacion habitacion = crearHabitacionSimulada(1L);
        habitacion.setActiva(activa);

        // Simula que existen habitaciones activas.
        when(habitacionRepository.findByActiva(activa)).thenReturn(List.of(habitacion));

        List<HabitacionResponse> resultado = habitacionService.findByActiva(activa);

        // Verifica que la respuesta sea válida.
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(activa, resultado.get(0).getActiva());

        // Verifica que se haya consultado por estado.
        verify(habitacionRepository).findByActiva(activa);
    }

    /**
     * Prueba findByCodigoTipo() cuando existen habitaciones con ese tipo.
     */
    @Test
    void findByCodigoTipo_DeberiaRetornarListaDeHabitaciones_CuandoCodigoExiste() {
        String codigoTipo = generateFakeCodigoTipo();

        Habitacion habitacion = crearHabitacionSimulada(1L);
        habitacion.setTipoHabitacion(crearTipoHabitacionSimulado(codigoTipo));

        // Simula que existen habitaciones asociadas al código de tipo.
        when(habitacionRepository.findByTipoHabitacionCodigo(codigoTipo)).thenReturn(List.of(habitacion));

        List<HabitacionResponse> resultado = habitacionService.findByCodigoTipo(codigoTipo);

        // Verifica que la respuesta sea válida.
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(codigoTipo, resultado.get(0).getCodigoTipo());

        // Verifica que se haya consultado por código de tipo.
        verify(habitacionRepository).findByTipoHabitacionCodigo(codigoTipo);
    }

    /**
     * Prueba create() cuando el request es válido y el número no está duplicado.
     */
    @Test
    void create_DeberiaCrearHabitacionYEnviarEvento_CuandoRequestEsValidoYNumeroEsUnico() {
        HabitacionRequest request = crearHabitacionRequestSimulado();

        TipoHabitacion tipoHabitacion = crearTipoHabitacionSimulado(request.getCodigoTipo());
        Habitacion habitacionSinId = crearHabitacionSinIdSimulada(request);
        Habitacion habitacionGuardada = crearHabitacionSimulada(100L);

        habitacionGuardada.setNumeroHabitacion(request.getNumeroHabitacion());
        habitacionGuardada.setPiso(request.getPiso());
        habitacionGuardada.setTipoHabitacion(tipoHabitacion);
        habitacionGuardada.setActiva(true);

        // Simula que no existe otra habitación con el mismo número.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(false);

        // Simula que el tipo de habitación existe.
        when(tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())).thenReturn(Optional.of(tipoHabitacion));

        // Simula que existe una tarifa activa para el tipo de habitación.
        when(tarifaLookupClient.existsTarifaActivaByTipoHabitacion(request.getCodigoTipo())).thenReturn(true);

        // Simula que el mapper convierte el request a entidad.
        when(habitacionMapper.toEntity(request)).thenReturn(habitacionSinId);

        // Simula que la base de datos asigna el ID al guardar.
        when(habitacionRepository.save(habitacionSinId)).thenReturn(habitacionGuardada);

        HabitacionResponse resultado = habitacionService.create(request);

        // Verifica que el servicio retorne una respuesta válida.
        assertNotNull(resultado);

        // Verifica que los datos retornados coincidan con lo esperado.
        assertEquals(100L, resultado.getId());
        assertEquals(request.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals(request.getPiso(), resultado.getPiso());
        assertEquals(request.getCodigoTipo(), resultado.getCodigoTipo());

        // Verifica que el service haya completado datos antes de guardar.
        assertEquals(true, habitacionSinId.getActiva());
        assertEquals(tipoHabitacion, habitacionSinId.getTipoHabitacion());

        // Verifica las llamadas esperadas a los mocks.
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());
        verify(tipoHabitacionRepository).findByCodigo(request.getCodigoTipo());
        verify(tarifaLookupClient).existsTarifaActivaByTipoHabitacion(request.getCodigoTipo());
        verify(habitacionRepository).save(habitacionSinId);

        // Verifica que se haya generado el evento de creación.
        verify(habitacionEventProducer).sendCreated(any(String.class), any(HabitacionCreatedEvent.class));
    }

    /**
     * Prueba create() cuando el número ya existe.
     */
    @Test
    void create_DeberiaLanzarDuplicateResourceException_CuandoNumeroHabitacionYaExiste() {
        HabitacionRequest request = crearHabitacionRequestSimulado();

        // Simula que ya existe una habitación con el mismo número.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(true);

        // Verifica que se lance excepción por duplicidad.
        assertThrows(DuplicateResourceException.class, () -> habitacionService.create(request));

        // Verifica que se haya validado la existencia del número.
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());

        // never(): verifica que estos métodos no hayan sido llamados.
        verify(tipoHabitacionRepository, never()).findByCodigo(anyString());
        verify(tarifaLookupClient, never()).existsTarifaActivaByTipoHabitacion(anyString());
        verify(habitacionRepository, never()).save(any(Habitacion.class));
        verify(habitacionEventProducer, never()).sendCreated(anyString(), any());
    }

    /**
     * Prueba create() cuando el tipo de habitación no existe.
     */
    @Test
    void create_DeberiaLanzarEntityNotFoundException_CuandoTipoHabitacionNoExiste() {
        HabitacionRequest request = crearHabitacionRequestSimulado();

        // Simula que el número no está duplicado.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(false);

        // Simula que no existe el tipo de habitación.
        when(tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())).thenReturn(Optional.empty());

        // Verifica que se lance la excepción esperada.
        assertThrows(EntityNotFoundException.class, () -> habitacionService.create(request));

        // Verifica las llamadas realizadas.
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());
        verify(tipoHabitacionRepository).findByCodigo(request.getCodigoTipo());

        // No debe consultar tarifa, guardar ni enviar evento si el tipo no existe.
        verify(tarifaLookupClient, never()).existsTarifaActivaByTipoHabitacion(anyString());
        verify(habitacionRepository, never()).save(any(Habitacion.class));
        verify(habitacionEventProducer, never()).sendCreated(anyString(), any());
    }

    /**
     * Prueba create() cuando no existe tarifa activa.
     */
    @Test
    void create_DeberiaLanzarEntityNotFoundException_CuandoNoExisteTarifaActiva() {
        HabitacionRequest request = crearHabitacionRequestSimulado();
        TipoHabitacion tipoHabitacion = crearTipoHabitacionSimulado(request.getCodigoTipo());

        // Simula que el número no está duplicado.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(false);

        // Simula que existe el tipo de habitación.
        when(tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())).thenReturn(Optional.of(tipoHabitacion));

        // Simula que no existe tarifa activa para ese tipo.
        when(tarifaLookupClient.existsTarifaActivaByTipoHabitacion(request.getCodigoTipo())).thenReturn(false);

        // Verifica que se lance la excepción esperada.
        assertThrows(EntityNotFoundException.class, () -> habitacionService.create(request));

        // Verifica las consultas realizadas.
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());
        verify(tipoHabitacionRepository).findByCodigo(request.getCodigoTipo());
        verify(tarifaLookupClient).existsTarifaActivaByTipoHabitacion(request.getCodigoTipo());

        // No debe guardar ni enviar evento si no hay tarifa activa.
        verify(habitacionRepository, never()).save(any(Habitacion.class));
        verify(habitacionEventProducer, never()).sendCreated(anyString(), any());
    }

    /**
     * Prueba update() cuando el número no cambia.
     */
    @Test
    void update_DeberiaActualizarHabitacion_CuandoNumeroNoCambia() {
        Long id = 5L;

        Habitacion habitacionExistente = crearHabitacionSimulada(id);
        HabitacionRequest request = crearHabitacionRequestSimulado();

        request.setNumeroHabitacion(habitacionExistente.getNumeroHabitacion());
        request.setPiso(faker.number().numberBetween(1, 20));
        request.setCodigoTipo(habitacionExistente.getTipoHabitacion().getCodigo());
        request.setActiva(null);

        TipoHabitacion tipoHabitacion = habitacionExistente.getTipoHabitacion();

        // Simula que la habitación existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacionExistente));

        // Simula que el tipo de habitación existe.
        when(tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())).thenReturn(Optional.of(tipoHabitacion));

        // Simula que save() retorna la misma habitación actualizada.
        when(habitacionRepository.save(habitacionExistente)).thenReturn(habitacionExistente);

        HabitacionResponse resultado = habitacionService.update(id, request);

        // Verifica que la respuesta no sea null.
        assertNotNull(resultado);

        // Verifica que los datos hayan sido actualizados.
        assertEquals(request.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals(request.getPiso(), resultado.getPiso());

        // No debe buscar duplicado porque el número no cambió.
        verify(habitacionRepository, never()).existsByNumeroHabitacion(anyString());

        // Verifica que se buscó, se actualizó y se guardó.
        verify(habitacionRepository).findById(id);
        verify(tipoHabitacionRepository).findByCodigo(request.getCodigoTipo());
        verify(habitacionMapper).updateEntity(request, habitacionExistente);
        verify(habitacionRepository).save(habitacionExistente);
    }

    /**
     * Prueba update() cuando el número cambia y no está duplicado.
     */
    @Test
    void update_DeberiaActualizarHabitacion_CuandoNumeroCambiaYNoEstaDuplicado() {
        Long id = 5L;

        Habitacion habitacionExistente = crearHabitacionSimulada(id);
        HabitacionRequest request = crearHabitacionRequestSimulado();

        request.setNumeroHabitacion(generateFakeNumeroHabitacion());

        TipoHabitacion tipoHabitacion = crearTipoHabitacionSimulado(request.getCodigoTipo());

        // Simula que la habitación existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacionExistente));

        // Simula que el nuevo número no está duplicado.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(false);

        // Simula que el tipo de habitación existe.
        when(tipoHabitacionRepository.findByCodigo(request.getCodigoTipo())).thenReturn(Optional.of(tipoHabitacion));

        // Simula que save() retorna la habitación actualizada.
        when(habitacionRepository.save(habitacionExistente)).thenReturn(habitacionExistente);

        HabitacionResponse resultado = habitacionService.update(id, request);

        // Verifica que la respuesta sea válida.
        assertNotNull(resultado);

        // Verifica que el número fue actualizado.
        assertEquals(request.getNumeroHabitacion(), resultado.getNumeroHabitacion());

        // Verifica las llamadas realizadas.
        verify(habitacionRepository).findById(id);
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());
        verify(tipoHabitacionRepository).findByCodigo(request.getCodigoTipo());
        verify(habitacionRepository).save(habitacionExistente);
    }

    /**
     * Prueba update() cuando el nuevo número ya pertenece a otra habitación.
     */
    @Test
    void update_DeberiaLanzarDuplicateResourceException_CuandoNumeroCambiaANumeroDuplicado() {
        Long id = 5L;

        Habitacion habitacionExistente = crearHabitacionSimulada(id);
        HabitacionRequest request = crearHabitacionRequestSimulado();

        request.setNumeroHabitacion(generateFakeNumeroHabitacion());

        // Simula que la habitación existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacionExistente));

        // Simula que el nuevo número ya está duplicado.
        when(habitacionRepository.existsByNumeroHabitacion(request.getNumeroHabitacion())).thenReturn(true);

        // Verifica que se lance excepción por duplicidad.
        assertThrows(DuplicateResourceException.class, () -> habitacionService.update(id, request));

        // Verifica las búsquedas realizadas.
        verify(habitacionRepository).findById(id);
        verify(habitacionRepository).existsByNumeroHabitacion(request.getNumeroHabitacion());

        // No debe guardar si hay duplicidad.
        verify(habitacionRepository, never()).save(any(Habitacion.class));
    }

    /**
     * Prueba cambiarActiva() cuando se cambia el estado activo de una habitación.
     */
    @Test
    void cambiarActiva_DeberiaCambiarEstadoActivo_CuandoIdExiste() {
        Long id = 1L;

        Habitacion habitacion = crearHabitacionSimulada(id);

        // Simula que la habitación existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacion));

        // Simula que save() retorna la habitación actualizada.
        when(habitacionRepository.save(habitacion)).thenReturn(habitacion);

        HabitacionResponse resultado = habitacionService.cambiarActiva(id, false);

        // Verifica que la respuesta no sea null.
        assertNotNull(resultado);

        // Verifica que el estado fue actualizado.
        assertFalse(resultado.getActiva());
        assertFalse(habitacion.getActiva());

        // Verifica que se buscó y se guardó.
        verify(habitacionRepository).findById(id);
        verify(habitacionRepository).save(habitacion);
    }

    /**
     * Prueba deleteById() cuando la habitación existe.
     */
    @Test
    void deleteById_DeberiaEliminarHabitacion_CuandoExiste() {
        Long id = 15L;

        Habitacion habitacion = crearHabitacionSimulada(id);

        // Simula que la habitación existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacion));

        habitacionService.deleteById(id);

        // Verifica las llamadas realizadas.
        verify(habitacionRepository).findById(id);
        verify(habitacionRepository).deleteById(id);
    }

    /**
     * Prueba deleteById() cuando la habitación no existe.
     */
    @Test
    void deleteById_DeberiaLanzarEntityNotFoundException_CuandoIdNoExiste() {
        Long id = 999L;

        // Simula que la habitación no existe.
        when(habitacionRepository.findById(id)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción esperada.
        assertThrows(EntityNotFoundException.class, () -> habitacionService.deleteById(id));

        // Verifica que se consultó la existencia del ID.
        verify(habitacionRepository).findById(id);

        // No debe eliminar si no existe.
        verify(habitacionRepository, never()).deleteById(any());
    }

    /**
     * Prueba findById() cuando el ID es nulo.
     */ 
    @Test
    void findById_DeberiaLanzarIllegalArgumentException_CuandoIdEsNulo() {
        // Verifica que se lance la excepción esperada.
        assertThrows(IllegalArgumentException.class, () -> habitacionService.findById(null));

        // No debe consultar el repositorio si el ID es nulo.
        verify(habitacionRepository, never()).findById(any());
    }
}