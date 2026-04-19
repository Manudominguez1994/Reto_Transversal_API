package apirest.reto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Destacado;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.model.entity.Usuario;
import apirest.reto.repository.EventoRepository;
import apirest.reto.repository.ReservaRepository;
import apirest.reto.repository.UsuarioRepository;
import apirest.reto.service.ReservaServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

	@Mock
	private ReservaRepository reservaRepository;

	@Mock
	private EventoRepository eventoRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private ReservaServiceImpl reservaService;

	@Test
	void hacerReserva_eventoNoExiste_devuelveError() {
		when(eventoRepository.findById(999)).thenReturn(Optional.empty());

		String resultado = reservaService.hacerReserva(999, "ana_g", 2, null);

		assertEquals("El evento no existe", resultado);
	}

	@Test
	void hacerReserva_eventoNoActivo_devuelveError() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.CANCELADO)
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));

		String resultado = reservaService.hacerReserva(1, "ana_g", 2, null);

		assertEquals("El evento no esta activo", resultado);
	}

	@Test
	void hacerReserva_cantidadSuperaDiez_devuelveError() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));

		String resultado = reservaService.hacerReserva(1, "ana_g", 11, null);

		assertEquals("La cantidad de entradas debe estar entre 1 y 10", resultado);
	}

	@Test
	void hacerReserva_usuarioYaTiene10Entradas_devuelveError() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.aforoMaximo(100)
				.precio(8.50)
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));
		when(reservaRepository.sumarCantidadPorEventoYUsuario(1, "ana_g")).thenReturn(9);

		String resultado = reservaService.hacerReserva(1, "ana_g", 3, null);

		assertEquals("Ya tienes 9 entradas para este evento. No puedes superar 10 en total", resultado);
	}

	@Test
	void hacerReserva_aforoSuperado_devuelveError() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.aforoMaximo(10)
				.precio(8.50)
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));
		when(reservaRepository.sumarCantidadPorEventoYUsuario(1, "ana_g")).thenReturn(0);
		when(reservaRepository.sumarCantidadPorEvento(1)).thenReturn(9);

		String resultado = reservaService.hacerReserva(1, "ana_g", 5, null);

		assertEquals("No hay suficientes plazas. Solo quedan 1 disponibles", resultado);
	}

	@Test
	void hacerReserva_todoOk_devuelveOk() {
		Evento evento = Evento.builder()
				.idEvento(1)
				.estado(Estado.ACTIVO)
				.aforoMaximo(100)
				.precio(8.50)
				.destacado(Destacado.S)
				.build();

		Usuario usuario = Usuario.builder()
				.username("ana_g")
				.build();

		when(eventoRepository.findById(1)).thenReturn(Optional.of(evento));
		when(reservaRepository.sumarCantidadPorEventoYUsuario(1, "ana_g")).thenReturn(0);
		when(reservaRepository.sumarCantidadPorEvento(1)).thenReturn(5);
		when(usuarioRepository.findById("ana_g")).thenReturn(Optional.of(usuario));

		String resultado = reservaService.hacerReserva(1, "ana_g", 2, null);

		assertEquals("ok", resultado);
	}

}
