package apirest.reto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Destacado;
import apirest.reto.model.entity.Evento.Estado;

public interface EventoRepository extends JpaRepository<Evento, Integer>{
	
	//“eventos con aforo máximo menor a una cantidad”
	List<Evento> findByAforoMaximoLessThan(int cantidad);
	
	//“eventos de un tipo concreto”
	List<Evento> findByTipo_IdTipo(int idTipo);

	//eventos con más de N reservas
	@Query("select r.evento "
			+ "from Reserva r "
			+ "group by r.evento "
			+ "having count(r) > ?1")
	List<Evento> buscarEventosConMasDeNReservas(int n);
	
	//"Buscar eventos por nombre que contengan una palabra"
	List<Evento> findByNombreContaining(String palabra);
	
	//“eventos de un tipo concreto (por nombre del tipo)”
	List<Evento> findByTipo_Nombre(String nombre);
	
	//“obtener todos los eventos con un estado concreto”
	List<Evento> findByEstado(Estado estado);
	
	// Obtener todos los eventos por destacado
	List<Evento> findByDestacado(Destacado destacado);
	
	//“eventos activos de un usuario” o “eventos de un usuario que están activos”
	@Query("select distinct r.evento "
			+ "from Reserva r "
			+ "where r.usuario.username = ?1 and r.evento.estado = ?2 ")
	List<Evento> buscarEventosActivosDeUnUsuario(String username, Estado estado);
	
	//“Buscar eventos por nombre, dirección o precio máximo”
	List<Evento> findByNombreOrDireccionOrPrecioLessThan(String nombre, String direccion, Double precio);
	
	// "Obtener el número total de eventos agrupados por estado (ACTIVO, TERMINADO, CANCELADO)" o
	// "Mostrar cuántos eventos hay en cada estado."
	@Query("select e.estado, count(e) "
			+ "from Evento e "
			+ "group by e.estado")
	List<Object[]> mostrarTotalDeEventosPorEstado();
	
	//"Buscar eventos con duración mayor que una cantidad."
	List<Evento> findByDuracionGreaterThan(int duracion);
	
	
}
