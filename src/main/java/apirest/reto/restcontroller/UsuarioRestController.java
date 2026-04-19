package apirest.reto.restcontroller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import apirest.reto.config.JwtUtil;
import apirest.reto.model.dto.UsuarioDto;
import apirest.reto.model.entity.Usuario;
import apirest.reto.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuarios", description = "Gestión de usuarios y registro")
@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtUtil jwtUtil;

	private boolean esAdmin(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) return false;
		String token = authHeader.substring(7);
		if (!jwtUtil.esTokenValido(token)) return false;
		return "ROLE_ADMON".equals(jwtUtil.obtenerRol(token));
	}

	@Operation(summary = "Listar todos los usuarios")
	@GetMapping("")
	public ResponseEntity<List<UsuarioDto>> findAll() {
		List<UsuarioDto> usuarios = usuarioService.findAll()
				.stream()
				.map(UsuarioDto::convertirAUsuarioDto)
				.toList();

		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<UsuarioDto> findById(@PathVariable String username){
		Usuario usuario = usuarioService.findById(username);
		
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(UsuarioDto.convertirAUsuarioDto(usuario));
		}
	}

	@Operation(summary = "Registro público de usuario", description = "Cualquiera puede registrarse. Se asigna ROLE_CLIENTE automáticamente.")
	// registro público - se le asigna ROLE_CLIENTE automaticamente
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@Valid @RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.registrar(usuario);

		if(nuevoUsuario == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El username ya esta en uso");
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDto.convertirAUsuarioDto(nuevoUsuario));
		}
	}

	@PostMapping("/alta")
	public ResponseEntity<?> insertOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@Valid @RequestBody Usuario usuario){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Usuario nuevoUsuario = usuarioService.insertOne(usuario);
		
		if(nuevoUsuario == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El username ya esta en uso");
		}else {
			return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDto.convertirAUsuarioDto(nuevoUsuario));
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> updateOne(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@Valid @RequestBody Usuario usuario){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Usuario usuarioActualizado = usuarioService.updateOne(usuario);
		
		if(usuarioActualizado == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(UsuarioDto.convertirAUsuarioDto(usuarioActualizado));
		}
	}
	
	@DeleteMapping("/eliminar/{username}")
	public ResponseEntity<?> deleteById(
			@RequestHeader(value = "Authorization", required = false) String authHeader,
			@PathVariable String username){

		if (!esAdmin(authHeader)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		int resultado = usuarioService.deleteById(username);
		
		if(resultado == 1) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//"Buscar usuarios cuyo email contenga un texto específico"
	@GetMapping("/email/{texto}")
	public ResponseEntity<List<UsuarioDto>> buscarPorEmail(@PathVariable String texto){
		List<UsuarioDto> usuarios = usuarioService.buscarPorEmail(texto)
				.stream()
				.map(UsuarioDto::convertirAUsuarioDto)
				.toList();
		
		return ResponseEntity.ok(usuarios);
	}
	
	//"usuarios que se registraron después de una fecha"
	@GetMapping("/fecha/{fechaRegistro}")
	public ResponseEntity<List<UsuarioDto>> buscarRegistradosDespuesDe(@PathVariable LocalDate fechaRegistro){
		List<UsuarioDto> usuarios = usuarioService.buscarRegistradosDespuesDe(fechaRegistro)
				.stream()
				.map(UsuarioDto::convertirAUsuarioDto)
				.toList();
		
		return ResponseEntity.ok(usuarios);
	}
	
	//"usuarios que hayan realizado alguna reserva"
	@GetMapping("/con-reserva")
	public ResponseEntity<List<UsuarioDto>> buscarUsuariosConAlgunaReserva(){
		List<UsuarioDto> usuarios = usuarioService.buscarUsuariosConAlgunaReserva()
				.stream()
				.map(UsuarioDto::convertirAUsuarioDto)
				.toList();
		return ResponseEntity.ok(usuarios);
	}
	
	//usuarios con más de N reservas.
	@GetMapping("/con-mas-de-n-reservas/{n}")
	public ResponseEntity<List<UsuarioDto>> buscarUsuariosConMasDeNReservas(@PathVariable int n){
		List<UsuarioDto> usuarios = usuarioService.buscarUsuariosConMasDeNReservas(n)
				.stream()
				.map(UsuarioDto::convertirAUsuarioDto)
				.toList();
		
		return ResponseEntity.ok(usuarios);
	}
	
}
