package apirest.reto.service;

import java.util.List;
import org.springframework.data.domain.Page;
import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Estado;

public interface EventoService {
	
	List<Evento> findAll();
	
	Page<Evento> findAllPaginado(int page, int size);
	
	Evento findById(int idEvento);
	
	Evento insertOne(Evento evento);
	
	Evento updateOne(Evento evento);
	
	int deleteById(int idEvento);
	
	//“eventos con aforo máximo menor a una cantidad”
	List<Evento> buscarPorAforoMaximoMenorQue(int cantidad);

	//“eventos de un tipo concreto”
	List<Evento> buscarPorTipo(int idTipo);
	
	//eventos con más de N reservas
	List<Evento> buscarEventosConMasDeNReservas(int n);
	
	//"Buscar eventos por nombre que contengan una palabra"
	List<Evento> buscarPorNombreQueContengaUnaPalabra(String palabra);
	
	//“eventos de un tipo concreto (por nombre del tipo)”
	List<Evento> buscarPorNombreDelTipo(String nombre);
	
	//“obtener todos los eventos con un estado concreto”
	List<Evento> buscarPorEstado(Estado estado);
	
	//“eventos de un usuario que están activos”
	List<Evento> buscarEventosActivosDeUnUsuario(String username);
	
	//“Buscar eventos por nombre, dirección o precio máximo”
	List<Evento> buscarPorNombreODireccionOPrecioMaximo(String nombre, String direccion, Double precio);
	
	// Obtener el número total de eventos agrupados por estado (ACTIVO, TERMINADO, CANCELADO) o
	// Mostrar total de eventos por estado.
	List<Object[]> mostrarTotalDeEventosPorEstado();
	
	//"Buscar eventos con duración mayor que una cantidad."
	List<Evento> buscarPorDuracionMayorQueUnaCantidad(int duracion);
	
	//"Dar de alta el evento poniendo por defecto estado = ACTIVO"
	Evento altaEventoActivo(Evento evento);
	
	//"Editar un evento existente por su id"
	Evento editarEventoPorId(int idEvento, Evento evento);
	
	//"Poner el estado del evento como cancelado por su id"
	Evento cancelarEvento(int idEvento);
	
	//Sacar el listado de todos los eventos activos
	List<Evento> buscarEventosPorEstadoActivo();
	
	//Sacar el listado de todos los eventos terminados
	List<Evento> buscarEventosPorEstadoTerminado();
	
	//Sacar el listado de todos los eventos cancelados
	List<Evento> buscarEventosPorEstadoCancelado();
	
	//Obtener todos los eventos destacados
	List<Evento> buscarEventosDestacados();

	// Pasa a TERMINADO todos los eventos ACTIVOS cuya fecha de inicio ya ha pasado
	void terminarEventosPasados();
}
