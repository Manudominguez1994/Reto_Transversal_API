package apirest.reto.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Reserva;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.repository.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService{
	
	@Autowired
	private ReservaRepository reservaRepository;

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

	//“reservas de un usuario”
	@Override
	public List<Reserva> buscarReservasPorUsuario(String username) {
		return reservaRepository.findByUsuario_Username(username);
	}

	//“reservas de un usuario con un evento activo”
	@Override
	public List<Reserva> buscarPorUsuarioConEventoActivo(String username) {
		return reservaRepository.findByUsuario_UsernameAndEvento_Estado(username, Estado.ACTIVO);
	}

	//“listar las reservas de un evento”
	@Override
	public List<Reserva> buscarPorEvento(int idEvento) {
		return reservaRepository.findByEvento_IdEvento(idEvento);
	}

	//“reservas por nombre del tipo de evento”
	@Override
	public List<Reserva> buscarPorNombreDeTipoDeEvento(String nombre) {
		return reservaRepository.findByEvento_Tipo_Nombre(nombre);
	}

	//“Reservas de usuarios con un perfil concreto”
	@Override
	public List<Reserva> buscarPorUsuariosConUnPerfilConcreto(int idPerfil) {
		return reservaRepository.buscarPorUsuariosConUnPerfilConcreto(idPerfil);
	}

	//“reservas de un usuario con evento activo en el último mes”
	@Override
	public List<Reserva> buscarPorUsuarioConEventoActivoEnElUltimoMes(String username) {
		return reservaRepository.findByUsuario_UsernameAndEvento_EstadoAndEvento_FechaInicioAfter(username, Estado.ACTIVO, LocalDate.now().minusMonths(1));
	}

	//Mostrar la suma total de reservas por usuario en eventos activos.
	@Override
	public List<Object[]> calcularCantidadTotalReservadaPorUsuarioEnEventosActivos() {
		return reservaRepository.calcularCantidadTotalReservadaPorUsuarioEnEventosActivos(Estado.ACTIVO);
	}
	
}
