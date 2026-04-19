package apirest.reto.service;

import java.util.List;
import apirest.reto.model.entity.Reserva;

public interface ReservaService {
	
	List<Reserva> findAll();
	
	Reserva findById(int idReserva);
	
	Reserva insertOne(Reserva reserva);
	
	Reserva updateOne(Reserva reserva);
	
	int deleteById(int idReserva);
	
	//“reservas de un usuario”
	List<Reserva> buscarReservasPorUsuario(String username);
	
	//“reservas de un usuario con un evento activo”
	List<Reserva> buscarPorUsuarioConEventoActivo(String username);
	
	//“listar las reservas de un evento”
	List<Reserva> buscarPorEvento(int idEvento);
	
	//“reservas por nombre del tipo de evento”
	List<Reserva> buscarPorNombreDeTipoDeEvento(String nombre);
	
	//“Reservas de usuarios con un perfil concreto”
	List<Reserva> buscarPorUsuariosConUnPerfilConcreto(int idPerfil);
	
	//“reservas de un usuario con evento activo en el último mes”
	List<Reserva> buscarPorUsuarioConEventoActivoEnElUltimoMes(String username);
	
	// “Mostrar la suma total de entradas reservadas por usuario en eventos activos”
	List<Object[]> calcularCantidadTotalReservadaPorUsuarioEnEventosActivos();
	
}
