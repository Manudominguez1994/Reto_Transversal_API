package apirest.reto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Destacado;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.repository.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService{

	@Autowired
	private EventoRepository eventoRepository;
	
	@Override
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	@Override
	public Page<Evento> findAllPaginado(int page, int size) {
		return eventoRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Evento findById(int idEvento) {
		return eventoRepository.findById(idEvento).orElse(null);
	}

	@Override
	public Evento insertOne(Evento evento) {
		return eventoRepository.save(evento); // status(HttpStatus.CREATED)
	}

	@Override
	public Evento updateOne(Evento evento) {
		if(eventoRepository.existsById(evento.getIdEvento())) {
			return eventoRepository.save(evento); // ok
		}else {
			return null; // notFound
		}
	}

	@Override
	public int deleteById(int idEvento) {
		if(eventoRepository.existsById(idEvento)) {
			eventoRepository.deleteById(idEvento);
			return 1; // noContent
		}else {
			return 0; // notFound
		}
	}

	//“eventos con aforo máximo menor a una cantidad”
	@Override
	public List<Evento> buscarPorAforoMaximoMenorQue(int cantidad) {
		return eventoRepository.findByAforoMaximoLessThan(cantidad);
	}

	//“eventos de un tipo concreto”
	@Override
	public List<Evento> buscarPorTipo(int idTipo) {
		return eventoRepository.findByTipo_IdTipo(idTipo);
	}
	
	//eventos con más de N reservas
	@Override
	public List<Evento> buscarEventosConMasDeNReservas(int n) {
		return eventoRepository.buscarEventosConMasDeNReservas(n);
	}

	//"Buscar eventos por nombre que contengan una palabra"
	@Override
	public List<Evento> buscarPorNombreQueContengaUnaPalabra(String palabra) {
		return eventoRepository.findByNombreContaining(palabra);
	}

	//“eventos de un tipo concreto (por nombre del tipo)”
	@Override
	public List<Evento> buscarPorNombreDelTipo(String nombre) {
		return eventoRepository.findByTipo_Nombre(nombre);
	}

	//“obtener todos los eventos con un estado concreto”
	@Override
	public List<Evento> buscarPorEstado(Estado estado) {
		return eventoRepository.findByEstado(estado);
	}

	//“eventos de un usuario que están activos”
	@Override
	public List<Evento> buscarEventosActivosDeUnUsuario(String username) {
		return eventoRepository.buscarEventosActivosDeUnUsuario(username, Estado.ACTIVO);
	}

	//“Buscar eventos por nombre, dirección o precio máximo”
	@Override
	public List<Evento> buscarPorNombreODireccionOPrecioMaximo(String nombre, String direccion, Double precio) {
		return eventoRepository.findByNombreOrDireccionOrPrecioLessThan(nombre, direccion, precio);
	}

	// Obtener el número total de eventos agrupados por estado (ACTIVO, TERMINADO, CANCELADO) o
	// Mostrar cuántos eventos hay en cada estado.
	@Override
	public List<Object[]> mostrarTotalDeEventosPorEstado() {
		return eventoRepository.mostrarTotalDeEventosPorEstado();
	}

	//"Buscar eventos con duración mayor que una cantidad."
	@Override
	public List<Evento> buscarPorDuracionMayorQueUnaCantidad(int duracion) {
		return eventoRepository.findByDuracionGreaterThan(duracion);
	}

	//"Dar de alta un evento poniendo por defecto estado = ACTIVO"
	@Override
	public Evento altaEventoActivo(Evento evento) {
		evento.setEstado(Estado.ACTIVO);
		return eventoRepository.save(evento);
	}

	//"Editar un evento existente por su id"
	@Override
	public Evento editarEventoPorId(int idEvento, Evento evento) {
		if (eventoRepository.existsById(idEvento)) {
			evento.setIdEvento(idEvento);
			return eventoRepository.save(evento);
		} else {
			return null;
		}
	}

	//"Poner el estado del evento como cancelado por su id"
	@Override
	public Evento cancelarEvento(int idEvento) {
		Evento evento = eventoRepository.findById(idEvento).orElse(null);
		
		if(evento == null) {
			return null;
		}else {
			evento.setEstado(Estado.CANCELADO);
			return eventoRepository.save(evento);
		}
	}

	//Sacar el listado de todos los eventos activos
	@Override
	public List<Evento> buscarEventosPorEstadoActivo() {
		return eventoRepository.findByEstado(Estado.ACTIVO);
	}

	//Obtener todos los eventos destacados
	@Override
	public List<Evento> buscarEventosDestacados() {
		return eventoRepository.findByDestacado(Destacado.S);
	}

	//Sacar el listado de todos los eventos terminados
	@Override
	public List<Evento> buscarEventosPorEstadoTerminado() {
		return eventoRepository.findByEstado(Estado.TERMINADO);
	}

	//Sacar el listado de todos los eventos cancelados
	@Override
	public List<Evento> buscarEventosPorEstadoCancelado() {
		return eventoRepository.findByEstado(Estado.CANCELADO);
	}
	
	


}
