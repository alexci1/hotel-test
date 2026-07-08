package cl.hilton.habitaciones;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Se desactiva contextLoads porque en pruebas unitarias no se levanta todo el contexto del microservicio.")
@SpringBootTest
class HotelHabitacionesApplicationTests {

	@Test
	void contextLoads() {
	}

}
