package apirest.reto.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apirest.reto.model.dto.ReservaDto;
import apirest.reto.model.entity.Reserva;
import apirest.reto.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/reservas")
public class ReservaRestController {
	
	@Autowired
	private ReservaService reservaService;
	
	@GetMapping("")
	public ResponseEntity<List<ReservaDto>> findAll() {
		List<ReservaDto> reservas = reservaService.findAll()
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();

		return ResponseEntity.ok(reservas);
	}
	
	@GetMapping("/{idReserva}")
	public ResponseEntity<ReservaDto> findById(@PathVariable int idReserva){
		Reserva reserva = reservaService.findById(idReserva);
		
		if(reserva == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(ReservaDto.convertirAReservaDto(reserva));
		}
	}
	
	@PostMapping("/alta")
	public ResponseEntity<ReservaDto> insertOne(@Valid @RequestBody Reserva reserva){
		Reserva nuevaReserva = reservaService.insertOne(reserva);
		return ResponseEntity.status(HttpStatus.CREATED).body(ReservaDto.convertirAReservaDto(nuevaReserva));
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<ReservaDto> updateOne(@Valid @RequestBody Reserva reserva){
		Reserva reservaActualizada = reservaService.updateOne(reserva);
		
		if(reservaActualizada == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(ReservaDto.convertirAReservaDto(reservaActualizada));
		}
	}
	
	@DeleteMapping("/eliminar/{idReserva}")
	public ResponseEntity<Void> deleteById(@PathVariable int idReserva){
		int resultado = reservaService.deleteById(idReserva);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//“reservas de un usuario”
	@GetMapping("/por-usuario/{username}")
	public ResponseEntity<List<ReservaDto>> buscarReservasPorUsuario(@PathVariable String username){
		List<ReservaDto> reservas = reservaService.buscarReservasPorUsuario(username)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		
		return ResponseEntity.ok(reservas);
	}
	
	//“reservas de un usuario con un evento activo”
	@GetMapping("/por-usuario-activo/{username}")
	public ResponseEntity<List<ReservaDto>> buscarPorUsuarioConEventoActivo(@PathVariable String username){
		List<ReservaDto> reservas = reservaService.buscarPorUsuarioConEventoActivo(username)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		
		return ResponseEntity.ok(reservas);
	}
	
	//“listar las reservas de un evento”
	@GetMapping("/por-evento/{idEvento}")
	public ResponseEntity<List<ReservaDto>> buscarPorEvento(@PathVariable int idEvento){
		List<ReservaDto> reservas = reservaService.buscarPorEvento(idEvento)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		return ResponseEntity.ok(reservas);
	}
	
	//“reservas por nombre del tipo de evento”
	@GetMapping("/por-nombre-de-tipo-de-evento/{nombre}")
	public ResponseEntity<List<ReservaDto>> buscarPorNombreDeTipoDeEvento(@PathVariable String nombre){
		List<ReservaDto> reservas = reservaService.buscarPorNombreDeTipoDeEvento(nombre)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		return ResponseEntity.ok(reservas);
	}
	
	//“Reservas de usuarios con un perfil concreto”
	@GetMapping("/perfil-concreto/{idPerfil}")
	public ResponseEntity<List<ReservaDto>> buscarPorUsuariosConUnPerfilConcreto(@PathVariable int idPerfil){
		List<ReservaDto> reservas = reservaService.buscarPorUsuariosConUnPerfilConcreto(idPerfil)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		return ResponseEntity.ok(reservas);
	}
	
	//“reservas de un usuario con evento activo en el último mes”
	@GetMapping("/usuario-evento-activo-ultimo-mes/{username}")
	public ResponseEntity<List<ReservaDto>> buscarPorUsuarioConEventoActivoEnElUltimoMes(@PathVariable String username){
		List<ReservaDto> reservas = reservaService.buscarPorUsuarioConEventoActivoEnElUltimoMes(username)
				.stream()
				.map(ReservaDto::convertirAReservaDto)
				.toList();
		return ResponseEntity.ok(reservas);
	}
	
	//“Mostrar la suma total de entradas reservadas por usuario en eventos activos”
	@GetMapping("/total-reservada-por-usuario-activo")
	public ResponseEntity<List<Object[]>> calcularCantidadTotalReservadaPorUsuarioEnEventosActivos(){
		return ResponseEntity.ok(reservaService.calcularCantidadTotalReservadaPorUsuarioEnEventosActivos());
	}
}