package apirest.reto.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.model.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{
	
	//“reservas de un usuario”
	List<Reserva> findByUsuario_Username(String username);
	
	//“reservas de un usuario con un evento activo”
	List<Reserva> findByUsuario_UsernameAndEvento_Estado(String username, Estado estado);
	
	//“listar las reservas de un evento”
	List<Reserva> findByEvento_IdEvento(int idEvento);
	
	//“reservas por nombre del tipo de evento”
	List<Reserva> findByEvento_Tipo_Nombre(String nombre);
	
	//“Reservas de usuarios con un perfil concreto”
	@Query("select distinct r "
		+ "from Reserva r, UsuarioPerfil up "
		+ "where r.usuario = up.usuario "
		+ "and up.perfil.idPerfil = ?1 ")
	List<Reserva> buscarPorUsuariosConUnPerfilConcreto(int idPerfil);
	
	//“reservas de un usuario con evento activo en el último mes”
	List<Reserva> findByUsuario_UsernameAndEvento_EstadoAndEvento_FechaInicioAfter(String username, Estado estado, LocalDate fechaInicio);
	
	//Mostrar la suma total de reservas por usuario en eventos activos.
	@Query("select r.usuario.username, sum(r.cantidad) "
			+ "from Reserva r "
			+ "where r.evento.estado = ?1 "
			+ "group by r.usuario.username")
	List<Object[]> calcularCantidadTotalReservadaPorUsuarioEnEventosActivos(Estado estado);

	// Suma de entradas ya reservadas para un evento concreto (para comprobar el aforo)
	@Query("select coalesce(sum(r.cantidad), 0) from Reserva r where r.evento.idEvento = ?1")
	int sumarCantidadPorEvento(int idEvento);

	// Suma de entradas que tiene un usuario en un evento concreto (maximo 10 por reserva)
	@Query("select coalesce(sum(r.cantidad), 0) from Reserva r where r.evento.idEvento = ?1 and r.usuario.username = ?2")
	int sumarCantidadPorEventoYUsuario(int idEvento, String username);

}
 