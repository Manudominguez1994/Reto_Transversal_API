package apirest.reto.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.model.entity.Reserva;
import apirest.reto.model.entity.Usuario;
import apirest.reto.repository.EventoRepository;
import apirest.reto.repository.ReservaRepository;
import apirest.reto.repository.UsuarioRepository;

@Service
public class ReservaServiceImpl implements ReservaService{
	
	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public List<Reserva> findAll() {
		return reservaRepository.findAll();
	}

	@Override
	public Reserva findById(int idReserva) {
		return reservaRepository.findById(idReserva).orElse(null);
	}

	@Override
	public Reserva insertOne(Reserva reserva) {
		return reservaRepository.save(reserva);
	}

	@Override
	public Reserva updateOne(Reserva reserva) {
		if(reservaRepository.existsById(reserva.getIdReserva())) {
			return reservaRepository.save(reserva);
		}else {
			return null;
		}
	}

	@Override
	public int deleteById(int idReserva) {
		if(reservaRepository.existsById(idReserva)) {
			reservaRepository.deleteById(idReserva);
			return 1;
		}else{
			return 0;
		}
	}

	//"reservas de un usuario"
	@Override
	public List<Reserva> buscarReservasPorUsuario(String username) {
		return reservaRepository.findByUsuario_Username(username);
	}

	//"reservas de un usuario con un evento activo"
	@Override
	public List<Reserva> buscarPorUsuarioConEventoActivo(String username) {
		return reservaRepository.findByUsuario_UsernameAndEvento_Estado(username, Estado.ACTIVO);
	}

	//"listar las reservas de un evento"
	@Override
	public List<Reserva> buscarPorEvento(int idEvento) {
		return reservaRepository.findByEvento_IdEvento(idEvento);
	}

	//"reservas por nombre del tipo de evento"
	@Override
	public List<Reserva> buscarPorNombreDeTipoDeEvento(String nombre) {
		return reservaRepository.findByEvento_Tipo_Nombre(nombre);
	}

	//"Reservas de usuarios con un perfil concreto"
	@Override
	public List<Reserva> buscarPorUsuariosConUnPerfilConcreto(int idPerfil) {
		return reservaRepository.buscarPorUsuariosConUnPerfilConcreto(idPerfil);
	}

	//"reservas de un usuario con evento activo en el último mes"
	@Override
	public List<Reserva> buscarPorUsuarioConEventoActivoEnElUltimoMes(String username) {
		return reservaRepository.findByUsuario_UsernameAndEvento_EstadoAndEvento_FechaInicioAfter(username, Estado.ACTIVO, LocalDate.now().minusMonths(1));
	}

	@Override
	public List<Object[]> calcularCantidadTotalReservadaPorUsuarioEnEventosActivos() {
		return reservaRepository.calcularCantidadTotalReservadaPorUsuarioEnEventosActivos(Estado.ACTIVO);
	}

	@Override
	public String hacerReserva(int idEvento, String username, int cantidad, String observaciones) {

		Evento evento = eventoRepository.findById(idEvento).orElse(null);
		if (evento == null) {
			return "El evento no existe";
		}

		if (!evento.getEstado().equals(Estado.ACTIVO)) {
			return "El evento no esta activo";
		}

		if (cantidad < 1 || cantidad > 10) {
			return "La cantidad de entradas debe estar entre 1 y 10";
		}

		// miramos cuantas entradas tiene ya este usuario en este evento
		int entradasDelUsuario = reservaRepository.sumarCantidadPorEventoYUsuario(idEvento, username);
		if (entradasDelUsuario + cantidad > 10) {
			return "Ya tienes " + entradasDelUsuario + " entradas para este evento. No puedes superar 10 en total";
		}

		// comprobamos que no se supera el aforo
		int entradasTotales = reservaRepository.sumarCantidadPorEvento(idEvento);
		int plazasLibres = evento.getAforoMaximo() - entradasTotales;
		if (cantidad > plazasLibres) {
			return "No hay suficientes plazas. Solo quedan " + plazasLibres + " disponibles";
		}

		Usuario usuario = usuarioRepository.findById(username).orElse(null);
		if (usuario == null) {
			return "El usuario no existe";
		}

		Reserva nuevaReserva = new Reserva();
		nuevaReserva.setEvento(evento);
		nuevaReserva.setUsuario(usuario);
		nuevaReserva.setCantidad(cantidad);
		nuevaReserva.setPrecioVenta(evento.getPrecio());
		nuevaReserva.setObservaciones(observaciones);
		reservaRepository.save(nuevaReserva);

		return "ok";
	}
	
}
