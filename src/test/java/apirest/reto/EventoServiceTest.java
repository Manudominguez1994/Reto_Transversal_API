package apirest.reto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Destacado;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.repository.EventoRepository;
import apirest.reto.service.EventoServiceImpl;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

	@Mock
	private EventoRepository eventoRepository;

	@InjectMocks
	private EventoServiceImpl eventoService;

	@Test
	void cancelarEvento_existente_devuelveEventoCancelado() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.nombre("Concierto Rock")
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));
		when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

		Evento resultado = eventoService.cancelarEvento(1);

		assertNotNull(resultado);
		assertEquals(Estado.CANCELADO, resultado.getEstado());
	}

	@Test
	void cancelarEvento_noExiste_devuelveNull() {
		when(eventoRepository.findById(999)).thenReturn(Optional.empty());

		Evento resultado = eventoService.cancelarEvento(999);

		assertNull(resultado);
	}

	@Test
	void altaEventoActivo_siempreSetaEstadoActivo() {
		Evento evento = Evento.builder()
				.nombre("Obra de Teatro")
				.estado(Estado.CANCELADO) // aunque llegue con otro estado, debe quedar ACTIVO
				.build();

		when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

		Evento resultado = eventoService.altaEventoActivo(evento);

		assertEquals(Estado.ACTIVO, resultado.getEstado());
	}

	@Test
	void terminarEventosPasados_marcaLosQueYaPasaron() {
		Evento eventoPasado = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.fechaInicio(LocalDate.now().minusDays(1))
				.destacado(Destacado.N)
				.build();

		Evento eventoFuturo = Evento.builder()
				.idEvento(2)
				.estado(Estado.ACTIVO)
				.fechaInicio(LocalDate.now().plusDays(5))
				.destacado(Destacado.N)
				.build();

		when(eventoRepository.findByEstado(Estado.ACTIVO)).thenReturn(Arrays.asList(eventoPasado, eventoFuturo));
		when(eventoRepository.save(any(Evento.class))).thenAnswer(i -> i.getArgument(0));

		eventoService.terminarEventosPasados();

		// solo se guarda el que ya paso
		verify(eventoRepository, times(1)).save(any(Evento.class));
		assertEquals(Estado.TERMINADO, eventoPasado.getEstado());
		assertEquals(Estado.ACTIVO, eventoFuturo.getEstado());
	}

	@Test
	void findAll_devuelveLista() {
		List<Evento> eventos = Arrays.asList(
				Evento.builder().idEvento(1).nombre("Evento A").build(),
				Evento.builder().idEvento(2).nombre("Evento B").build()
		);

		when(eventoRepository.findAll()).thenReturn(eventos);

		List<Evento> resultado = eventoService.findAll();

		assertEquals(2, resultado.size());
	}

}
