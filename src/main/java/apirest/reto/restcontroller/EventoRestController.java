package apirest.reto.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import apirest.reto.config.JwtUtil;
import apirest.reto.model.dto.EventoDto;
import apirest.reto.model.entity.Evento;
import apirest.reto.model.entity.Evento.Estado;
import apirest.reto.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Eventos", description = "Gestión de eventos del cine")
@RestController
@RequestMapping("/eventos")
public class EventoRestController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private JwtUtil jwtUtil;

	private boolean esAdmin(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
		String token = authHeader.substring(7);
		if (!jwtUtil.esTokenValido(token)) return false;
		return "ROLE_ADMON".equals(jwtUtil.obtenerRol(token));
	}

	@Operation(summary = "Listar todos los eventos")
	@GetMapping("")
	public ResponseEntity<List<EventoDto>> findAll() {
		List<EventoDto> eventos = eventoService.findAll()
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<EventoDto>> findAllPaginado(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Page<EventoDto> eventos = eventoService.findAllPaginado(page, size)
				.map(EventoDto::convertirAEventoDto);

		return ResponseEntity.ok(eventos);
	}

	@Operation(summary = "Buscar evento por id")
	@GetMapping("/{idEvento}")
	public ResponseEntity<EventoDto> findById(@PathVariable int idEvento) {
		Evento evento = eventoService.findById(idEvento);

		if (evento == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(EventoDto.convertirAEventoDto(evento));
		}
	}

	@PostMapping("/alta")
	public ResponseEntity<?> insertOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@Valid @RequestBody Evento evento) {

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Evento nuevoEvento = eventoService.insertOne(evento);
		return ResponseEntity.status(HttpStatus.CREATED).body(EventoDto.convertirAEventoDto(nuevoEvento));
	}

	@PutMapping("/actualizar")
	public ResponseEntity<?> updateOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@Valid @RequestBody Evento evento) {

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Evento eventoActualizado = eventoService.updateOne(evento);

		if (eventoActualizado == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(EventoDto.convertirAEventoDto(eventoActualizado));
		}
	}

	@DeleteMapping("/eliminar/{idEvento}")
	public ResponseEntity<?> deleteOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idEvento) {

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		int resultado = eventoService.deleteById(idEvento);

		if (resultado == 1) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/aforo-maximo-menor-de/{cantidad}")
	public ResponseEntity<List<EventoDto>> buscarPorAforoMaximoMenorQue(@PathVariable int cantidad){
		List<EventoDto> eventos = eventoService.buscarPorAforoMaximoMenorQue(cantidad)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	//"eventos de un tipo concreto"
	@GetMapping("/tipo/{idTipo}")
	public ResponseEntity<List<EventoDto>> buscarPorTipo(@PathVariable int idTipo){
		List<EventoDto> eventos = eventoService.buscarPorTipo(idTipo)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	//eventos con más de N reservas
	@GetMapping("/con-mas-de-n-reservas/{n}")
	public ResponseEntity<List<EventoDto>> buscarEventosConMasDeNReservas(@PathVariable int n){
		List<EventoDto> eventos = eventoService.buscarEventosConMasDeNReservas(n)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventos);
	}

	//"Buscar eventos por nombre que contengan una palabra"
	@GetMapping("/por-nombre/{palabra}")
	public ResponseEntity<List<EventoDto>> buscarPorNombreQueContengaUnaPalabra(@PathVariable String palabra){
		List<EventoDto> eventos = eventoService.buscarPorNombreQueContengaUnaPalabra(palabra)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	//"eventos de un tipo concreto (por nombre del tipo)"
	@GetMapping("/por-nombre-del-tipo/{nombre}")
	public ResponseEntity<List<EventoDto>> buscarPorNombreDelTipo(@PathVariable String nombre){
		List<EventoDto> eventos = eventoService.buscarPorNombreDelTipo(nombre)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	//"obtener todos los eventos con un estado concreto"
	@GetMapping("/estado/{estado}")
	public ResponseEntity<List<EventoDto>> buscarPorEstado(@PathVariable Estado estado){
		List<EventoDto> estados = eventoService.buscarPorEstado(estado)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(estados);
	}

	//"eventos de un usuario que están activos"
	@GetMapping("/activos-de-un-usuario/{username}")
	public ResponseEntity<List<EventoDto>> buscarEventosActivosDeUnUsuario(@PathVariable String username){
		List<EventoDto> eventos = eventoService.buscarEventosActivosDeUnUsuario(username)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventos);
	}

	//"Buscar eventos por nombre, dirección o precio máximo"
	@GetMapping("/por-nombre-o-direccion-o-precio-maximo")
	public ResponseEntity<List<EventoDto>> buscarPorNombreODireccionOPrecioMaximo(
			@RequestParam(required=false) String nombre,
			@RequestParam(required=false) String direccion,
			@RequestParam(required=false) Double precio){
		List<EventoDto> eventos = eventoService.buscarPorNombreODireccionOPrecioMaximo(nombre, direccion, precio)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventos);
	}

	@GetMapping("/total-por-estado")
	public ResponseEntity<List<Object[]>> mostrarTotalDeEventosPorEstado(){
		return ResponseEntity.ok(eventoService.mostrarTotalDeEventosPorEstado());
	}

	//"Buscar eventos con duración mayor que una cantidad."
	@GetMapping("/duracion-mayor-que/{duracion}")
	public ResponseEntity<List<EventoDto>> buscarPorDuracionMayorQueUnaCantidad(@PathVariable int duracion){
		List<EventoDto> eventos = eventoService.buscarPorDuracionMayorQueUnaCantidad(duracion)
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventos);
	}

	@Operation(summary = "Dar de alta un evento (queda ACTIVO)", description = "Solo administradores")
	@PostMapping("/alta-activo")
	public ResponseEntity<?> altaEventoActivo(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@Valid @RequestBody Evento evento){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Evento nuevoEventoActivo = eventoService.altaEventoActivo(evento);
		return ResponseEntity.status(HttpStatus.CREATED).body(EventoDto.convertirAEventoDto(nuevoEventoActivo));
	}

	@PutMapping("/editar/{idEvento}")
	public ResponseEntity<?> editarEvento(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idEvento,
			@Valid @RequestBody Evento evento){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Evento eventoEditado = eventoService.editarEventoPorId(idEvento, evento);

		if (eventoEditado == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(EventoDto.convertirAEventoDto(eventoEditado));
		}
	}

	@Operation(summary = "Cancelar un evento por id", description = "Solo administradores")
	@PutMapping("/cancelar/{idEvento}")
	public ResponseEntity<?> cancelarEvento(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable int idEvento){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Evento eventoCancelado = eventoService.cancelarEvento(idEvento);

		if(eventoCancelado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(EventoDto.convertirAEventoDto(eventoCancelado));
		}
	}

	@Operation(summary = "Listar eventos activos")
	//Sacar el listado de todos los eventos activos
	@GetMapping("/por-estado-activo")
	public ResponseEntity<List<EventoDto>> buscarEventosPorEstadoActivo(){
		List<EventoDto> eventosActivos = eventoService.buscarEventosPorEstadoActivo()
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventosActivos);
	}

	@Operation(summary = "Listar eventos destacados")
	//Obtener todos los eventos destacados
	@GetMapping("/destacados")
	public ResponseEntity<List<EventoDto>> buscarEventosDestacados(){
		List<EventoDto> eventosDestacados = eventoService.buscarEventosDestacados()
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();

		return ResponseEntity.ok(eventosDestacados);
	}

	//Sacar el listado de todos los eventos terminados
	@GetMapping("/terminados")
	public ResponseEntity<List<EventoDto>> buscarEventosPorEstadoTerminado(){
		List<EventoDto> eventosTerminados = eventoService.buscarEventosPorEstadoTerminado()
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventosTerminados);
	}

	//Sacar el listado de todos los eventos cancelados
	@GetMapping("/cancelados")
	public ResponseEntity<List<EventoDto>> buscarEventosPorEstadoCancelado(){
		List<EventoDto> eventosCancelados = eventoService.buscarEventosPorEstadoCancelado()
				.stream()
				.map(EventoDto::convertirAEventoDto)
				.toList();
		return ResponseEntity.ok(eventosCancelados);
	}
}
